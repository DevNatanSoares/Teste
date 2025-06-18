# Projeto de Testes com Mockito

## Objetivo

Este projeto tem como foco **testes unitários** com Mockito e JUnit 5, cobrindo os seguintes tópicos:

- `when(...).thenReturn(...)`
- `verify(...)`
- `assertThrows(...)`
- `Assume.assumeTrue(...)`

## Estrutura do Projeto

- `Usuario.java` — classe simples com nome, e-mail e flag de ativo.
- `UsuarioRepository.java` — simula acesso a dados.
- `EmailService.java` — simula envio de e-mails.
- `NotificacaoService.java` — envia e-mails para usuários ativos.
- `NotificacaoServiceTest.java` — onde você escreverá seus testes.

---

## O que você deve testar

### Envio para usuários ativos
- Simule uma lista com usuários ativos e inativos.
- Use `when(...).thenReturn(...)` para simular o retorno do repositório.
- Use `verify(...)` para garantir que **somente os usuários ativos** receberam e-mail.

### Lista vazia
- Simule o retorno de uma lista vazia de usuários.
- Verifique que **nenhum e-mail foi enviado** (`verify(..., never())`).

### Exceção ao enviar e-mail
- Simule uma exceção lançada pelo `EmailService`.
- Use `assertThrows(...)` para verificar que a exceção foi propagada corretamente.

### Assumptions
- Crie um teste que só deve continuar se houver ao menos um usuário ativo.
- Use `assumeTrue(...)` para **ignorar o teste** caso contrário.

## Próprio

- Crie pelo menos **1 teste novo inventado por você** que utilize verify. Não pode ser um teste simples demais

---

## Observação

- Você **não precisa implementar nenhuma lógica nova**.
