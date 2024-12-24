O **Retry Pattern** (Padrão de Repetição) é uma estratégia utilizada para lidar com falhas temporárias em sistemas distribuídos. Quando uma operação falha, o padrão tenta repetir a execução da operação por um número determinado de vezes ou até que um critério de sucesso seja atendido. Este padrão é especialmente útil em sistemas que fazem chamadas a serviços externos ou têm dependências que podem ter falhas intermitentes (como serviços de banco de dados, APIs, etc.).

Como Funciona o Retry Pattern
Tentativas: Você configura o número de tentativas que a operação pode fazer antes de desistir. Por exemplo, em meu caso, são 3 tentativas.

Intervalo entre tentativas: Após cada falha, o padrão pode ter um intervalo entre as tentativas. Esse intervalo pode ser fixo, progressivo (aumentando a cada tentativa) ou aleatório.

Timeout (Tempo de Execução Máximo): A operação tem um tempo máximo para ser executada, após o qual é interrompida. No nosso caso,  um Time To Live (TTL) de 10.000 segundos.

Após as tentativas de reprocessamento, se a operação não for bem-sucedida, um dos padrões que estamos adotando é encaminhar a solicitação para uma fila de análise para um processamento posterior. Esse é um comportamento comum em sistemas distribuídos para garantir que as operações não sejam perdidas, permitindo uma análise detalhada e um novo tratamento em outro momento.

No nosso caso, a fila foi chamada de consulta-emprestimo-parking-lot, o que sugere que as operações que falharam, após todas as tentativas de reprocessamento, serão "estacionadas" temporariamente nesta fila para uma análise futura.

---

# Como Executar uma Aplicação Java 21 com Maven

Este guia explica como executar uma aplicação Java 21 utilizando o Maven, desde a configuração até a execução do projeto.

## Pré-requisitos

Antes de começar, certifique-se de que você tem as seguintes ferramentas instaladas:

1. **Java 21**: A versão 21 do JDK deve estar instalada no seu sistema.
2. **Maven**: Maven é a ferramenta de construção que usaremos para gerenciar dependências e construir o projeto.
3. **IDE (opcional)**: Embora não seja obrigatório, é recomendado utilizar uma IDE como IntelliJ IDEA, Eclipse ou VSCode para facilitar o desenvolvimento.
