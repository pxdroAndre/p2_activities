# WePayU - Sistema de Folha de Pagamento

![Java](https://img.shields.io/badge/Java-17+-blue?style=for-the-badge&logo=java)

## 📖 Sobre o Projeto

**WePayU** é um sistema de folha de pagamento robusto e flexível, desenvolvido em Java. Ele gerencia o ciclo de vida completo dos empregados, desde a criação até a remoção, processando pagamentos para diferentes tipos de contratos: **assalariados**, **horistas** e **comissionados**.

O sistema foi projetado com foco em extensibilidade e manutenibilidade, utilizando padrões de projeto consagrados para garantir um código limpo, coeso e de fácil evolução.

---

## ✨ Funcionalidades Principais

*   **Gerenciamento Completo de Empregados**:
    *   Criação, consulta, atualização e remoção de empregados.
    *   Busca de empregados por nome e índice.
*   **Múltiplos Tipos de Contrato**:
    *   **Horista**: Pagamento baseado em horas trabalhadas, com suporte a horas extras.
    *   **Assalariado**: Pagamento de salário fixo mensal.
    *   **Comissionado**: Pagamento de um salário base mais comissão sobre as vendas.
*   **Processamento da Folha de Pagamento**:
    *   Cálculo do valor total da folha em uma data específica.
    *   Geração de um arquivo de saída (`.txt`) com o resumo detalhado dos pagamentos (`rodaFolha`).
*   **Flexibilidade de Pagamento**:
    *   Suporte a múltiplos métodos de pagamento: em mãos, correios ou depósito em conta bancária.
    *   Agendas de pagamento personalizáveis (semanal, quinzenal, mensal).
*   **Gestão Sindical**:
    *   Afiliação de empregados a um sindicato.
    *   Lançamento de taxas de serviço e cálculo de descontos sindicais.
*   **Lançamentos Específicos**:
    *   Registro de cartões de ponto para horistas.
    *   Registro de resultados de vendas para comissionados.
*   **Undo/Redo**:
    *   Capacidade de desfazer e refazer qualquer ação que modifique o estado do sistema (criação, alteração, remoção, etc.).
*   **Persistência de Dados**:
    *   O estado completo do sistema (empregados, lançamentos, etc.) é salvo em um arquivo `database.xml` ao encerrar, garantindo a continuidade dos dados entre as sessões.

---

## 🏛️ Arquitetura e Design

O projeto foi estruturado em pacotes para separar responsabilidades e utiliza diversos padrões de projeto para garantir a qualidade do software.

### Estrutura de Pacotes

```
br.ufal.ic.p2.wepayu
├── commands/      # Implementações do padrão Command para Undo/Redo
├── Exception/     # Exceções customizadas para tratamento de erros de negócio
├── models/        # Classes de domínio (Empregado, SistemaFolha, etc.)
├── utilities/     # Classes utilitárias (ex: Clonador de objetos)
└── Facade.java    # Ponto de entrada do sistema (padrão Facade)
```

### Padrões de Projeto Utilizados

*   **Facade**: A classe `Facade.java` simplifica a interação com o sistema, provendo uma interface única e coesa para todas as operações.
*   **Command**: Permite o encapsulamento de operações como objetos, viabilizando a implementação robusta das funcionalidades de `undo` e `redo`.
*   **Strategy**: A lógica de cálculo de salário e verificação de dia de pagamento é delegada às classes de empregado específicas, permitindo que diferentes algoritmos sejam aplicados de forma intercambiável.
*   **Singleton (implícito)**: A instância de `SistemaFolha` é gerenciada pela `Facade`, agindo como um ponto central de controle do estado da aplicação.

---

## 🚀 Como Executar e Testar

O projeto utiliza a ferramenta **EasyAccept** para a execução de testes de aceitação baseados em scripts.

### Pré-requisitos

*   JDK 17 ou superior.
*   O arquivo `easyaccept.jar` (não incluído neste repositório) deve estar no classpath do projeto.

### Executando os Testes

1.  Compile todas as classes do projeto.
2.  Execute a classe `Main` fornecendo como argumentos a `Facade` do sistema e os arquivos de script de teste.

**Exemplo de comando (via terminal):**

```bash
# Supondo que as classes compiladas estão na pasta 'bin' e o easyaccept.jar está na pasta 'lib'
java -cp "lib/easyaccept.jar;bin" Main br.ufal.ic.p2.wepayu.Facade tests/us1.txt tests/us2.txt ...
```

---

## ✍️ Autor

*   **pxdroAndre**

---