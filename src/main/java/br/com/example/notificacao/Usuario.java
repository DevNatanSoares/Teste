
package br.com.example.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

public class NotificacaoServiceTest {

    private UsuarioRepository usuarioRepository;
    private EmailService emailService;
    private NotificacaoService notificacaoService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        emailService = mock(EmailService.class);
        notificacaoService = new NotificacaoService(usuarioRepository, emailService);
    }

    @Test
    void deveEnviarEmailApenasParaUsuariosAtivos() {
        List<Usuario> usuarios = Arrays.asList(
            new Usuario("João", "joao@email.com", true),
            new Usuario("Maria", "maria@email.com", false)
        );

        when(usuarioRepository.buscarTodos()).thenReturn(usuarios);

        notificacaoService.notificarUsuarios();

        verify(emailService).enviarEmail(usuarios.get(0));
        verify(emailService, never()).enviarEmail(usuarios.get(1));
    }

    @Test
    void naoDeveEnviarEmailsSeListaEstiverVazia() {
        when(usuarioRepository.buscarTodos()).thenReturn(Collections.emptyList());

        notificacaoService.notificarUsuarios();

        verify(emailService, never()).enviarEmail(any());
    }

    @Test
    void deveLancarExcecaoQuandoEmailFalhar() {
        Usuario usuario = new Usuario("Ana", "ana@email.com", true);
        when(usuarioRepository.buscarTodos()).thenReturn(List.of(usuario));
        doThrow(new RuntimeException("Falha ao enviar")).when(emailService).enviarEmail(usuario);

        assertThrows(RuntimeException.class, () -> notificacaoService.notificarUsuarios());
    }

    @Test
    void deveTestarApenasSeHouverUsuarioAtivo() {
        List<Usuario> usuarios = List.of(
            new Usuario("Bruno", "bruno@email.com", false)
        );

        when(usuarioRepository.buscarTodos()).thenReturn(usuarios);

        boolean temAtivo = usuarios.stream().anyMatch(Usuario::ativo);
        assumeTrue(temAtivo);

        notificacaoService.notificarUsuarios();

        verify(emailService, atLeastOnce()).enviarEmail(any());
    }

    @Test
    void deveChamarBuscaDeUsuariosUmaUnicaVez() {
        List<Usuario> usuarios = List.of(
            new Usuario("Leo", "leo@email.com", true)
        );

        when(usuarioRepository.buscarTodos()).thenReturn(usuarios);

        notificacaoService.notificarUsuarios();

        verify(usuarioRepository, times(1)).buscarTodos();
    }
}