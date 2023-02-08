<table>
<tr>
<td>
<a href= "https://ael.com.br/"><img src="https://www.ael.com.br/images/ael.png" alt="AEL Sistemas" border="0" width="70%"></a>
</td>
<td><a href= "https://www.inteli.edu.br/"><img src="https://www.inteli.edu.br/wp-content/uploads/2021/08/20172028/marca_1-2.png" alt="Inteli - Instituto de Tecnologia e Liderança" border="0" width="30%"></a>
</td>
</tr>
</table>

<font size="+12"><center>
Planejador de trajetórias para voos em baixa altitude
</center></font>

>*Observação 1: A estrutura inicial deste documento é só um exemplo. O seu grupo deverá alterar esta estrutura de acordo com o que está sendo solicitado nos artefatos.*

>*Observação 2: O índice abaixo não precisa ser editado se você utilizar o Visual Studio Code com a extensão **Markdown All in One**. Essa extensão atualiza o índice automaticamente quando o arquivo é salvo.*

**Conteúdo**

- [Autores](#autores)
- [Visão Geral do Projeto](#visão-geral-do-projeto)
  - [Empresa](#empresa)
  - [O Problema](#o-problema)
  - [Objetivos](#objetivos)
    - [Objetivos gerais](#objetivos-gerais)
    - [Objetivos específicos](#objetivos-específicos)
  - [Partes interessadas](#partes-interessadas)
- [Análise do Problema](#análise-do-problema)
  - [Análise da área de atuação](#análise-da-área-de-atuação)
    - [Principais Players](#principais-players)
    - [Cinco Forças de Porter](#cinco-forças-de-porter)
      - [Rivalidade entre concorrentes](#rivalidade-entre-concorrentes)
      - [Poder de barganha dos fornecedores](#poder-de-barganha-dos-fornecedores)
      - [Poder de barganha dos compradores](#poder-de-barganha-dos-compradores)
      - [Ameaça de novos entrantes](#ameaça-de-novos-entrantes)
      - [Ameaça de produtos ou serviços substitutos](#ameaça-de-produtos-ou-serviços-substitutos)
    - [Tendências](#tendências)
    - [Modelo de Negócios](#modelo-de-negócios)
  - [Análise do cenário: Matriz SWOT](#análise-do-cenário-matriz-swot)
  - [Proposta de Valor: Value Proposition Canvas](#proposta-de-valor-value-proposition-canvas)
  - [Matriz de Risco](#matriz-de-risco)
- [Requisitos do Sistema](#requisitos-do-sistema)
  - [Personas](#personas)
  - [Histórias dos usuários (user stories)](#histórias-dos-usuários-user-stories)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
  - [Módulos do Sistema e Visão Geral (Big Picture)](#módulos-do-sistema-e-visão-geral-big-picture)
  - [Descrição dos Subsistemas](#descrição-dos-subsistemas)
    - [Requisitos de software](#requisitos-de-software)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [UX e UI Design](#ux-e-ui-design)
  - [Wireframe + Storyboard](#wireframe--storyboard)
  - [Design de Interface - Guia de Estilos](#design-de-interface---guia-de-estilos)
- [Projeto de Banco de Dados](#projeto-de-banco-de-dados)
  - [Modelo Conceitual](#modelo-conceitual)
  - [Modelo Lógico](#modelo-lógico)
- [Teste de Software](#teste-de-software)
  - [Testes Unitários](#testes-unitários)
  - [Teste de Usabilidade](#teste-de-usabilidade)
- [Análise de Dados](#análise-de-dados)
- [Manuais](#manuais)
  - [Manual de Implantação](#manual-de-implantação)
  - [Manual do Usuário](#manual-do-usuário)
  - [Manual do Administrador](#manual-do-administrador)
- [Referências](#referências)


# Autores

* Aluno 1
* Aluno 2
* Aluno 3
* Aluno 4
* Aluno 5
* Aluno 6
* Aluno 7
* Aluno 8


# Visão Geral do Projeto

## Empresa

A AEL Sistemas é uma empresa brasileira, parte do grupo Elbit Systems, situada em Porto Alegre que dedica-se ao projeto, desenvolvimento, fabricação, manutenção e suporte logístico de sistemas eletrônicos militares e espaciais, para aplicações em plataformas aéreas, marítimas e terrestres. Capacitada para o fornecimento, projeto e desenvolvimento de aviônicos, sistemas terrestres e sistemas para segurança pública, a empresa também participa de diversos programas da indústria espacial. Sua missão é viabilizar soluções confiáveis e inovadoras que ampliem as capacidades dos clientes, com foco nos segmentos Aeroespacial, Defesa e Segurança. Software e sistemas são o foco de produção do time de desenvolvimento, com investimentos constantes na capacitação de pessoas e em profissionais altamente qualificados. Seus principais valores são: Foco no Cliente, Competência, Flexibilidade, Inovação e Excelência, Valorização das Pessoas e Sustentabilidade, Credibilidade e Respeito.


## O Problema

*Descrição_do_problema*

## Objetivos

### Objetivos gerais

*Lista_de_objetivos_gerais*

### Objetivos específicos

*Lista_de_objetivos específicos*

## Partes interessadas

*Lista_e_apresentação_das_partes_interessadas*

# Análise do Problema

*Descrição_da_análise_do_problema*

## Análise da área de atuação

### Principais Players

Os principais players globais do setor de
fabricação de componentes de aviação e aeroespaciais e de defesa são: <br>
1. Lockheed Martin - É a maior empresa de armamentos do mundo, atuando também no setor aeroespacial e de aviação. Na área de Defesa, uma de suas especialidades são os caças e aviões militares.
2. BAE Systems - É a segunda maior empresa do mundo no setor de Defesa, além de atuar também em aviação, tecnologia aeroespacial e segurança. Seus principais projetos envolvem a produção de caças.
3. Boeing - Apesar de ser mais conhecida pela aviação comercial, também atua no desenvolvimento de caças, aviões militares, sistemas eletrônicos, mísseis e tecnologia aeroespacial.
4. Northrop Grumman - É a quarta maior empresa do setor. A empresa vem apostando na área de drones para missões de espionagem e ataque.
5. Elbit Systems - É uma empresa internacional de alta tecnologia engajada nas áreas de defesa e segurança nacional. Desenvolvem e fornecem sistemas e produtos aerotransportados, terrestres e navais para defesa, segurança interna e aplicações comerciais. Além disso, fornecem uma gama de serviços de treinamento e suporte.

Os principais players brasileiros, além da AEL são:
1. Helibras - Única fabricante de helicópteros do Brasil, a Helibras pertence ao grupo europeu EADS. A empresa produz tanto modelos civis quanto militares.
2. Embraer - Desenvolve e opera sistemas de comunicação, computação, comando, controle e inteligência.
3. Avibras - Dedica-se ao projeto e fabricação de mísseis e sistemas de defesa ar-terra e terra-terra, além de aviões não tripulados. Desenvolve tecnologia nas áreas de aeronáutica, espaço, eletrônica, veicular e defesa.
4. Atech - empresa especializada no desenvolvimento de soluções de missões críticas, como sistemas de comando e controle, segurança cibernética, sistemas embarcados, simuladores, sistemas de instrumentação e controle e treinamento.
5. Omnisys Engenharia - Presta serviços em sistemas para aplicações aeronáuticas e navais e soluções para as áreas espacial, de telecomunicações e atividades industriais, soluções de guerra eletrônica e software embarcado.

### Cinco Forças de Porter

#### Rivalidade entre concorrentes
As empresas do setor, no geral, desenvolvem produtos e serviços parecidos entre si. Dessa forma, a concorrência entre elas aumenta, já que suas atuações são semelhantes. Assim, as empresas devem focar na inovação para se diferenciarem nesse ambiente rivalizado.

#### Poder de barganha dos fornecedores

A AEL Sistemas fabrica componentes de aviação e aeroespaciais. Dessa forma, fornecedores de elementos eletrônicos para essas soluções podem ter um poder de barganha maior, já que alguns desses elementos, como microprocessadores, são escassos no mercado (crise de matéria-prima para esses componentes) e com poucos fornecedores. Além disso, a restrição de fornecedores por parte dos clientes também contribuem para um poder de barganha menor. Assim, a AEL não teria muitas opções além de aceitar os termos de seus fornecedores.

#### Poder de barganha dos compradores

A AEL Sistemas tem uma grande ligação com projetos das Forças Armadas Brasileiras há alguns anos. Nesse caso, a AEL teria um poder de barganha maior pela confiança e pela parceria. Contudo, em um cenário global, a grande quantidade de players pode fazer com que o poder de barganha dos compradores aumente por ter mais opções de mercado.

#### Ameaça de novos entrantes

O setor da AEL já é consolidado e com uma grande concorrência entre seus players. Assim, a ameaça de novos entrantes não é tão alta, já que as maiores empresas do setor têm a confiança do mercado e se dedicam constantemente para a inovação na área. Além disso, a boa relação entre a AEL e as Forças Armadas faz com que novos entrantes no Brasil tenham dificuldade em ameaçar a atual posição.

#### Ameaça de produtos ou serviços substitutos

O setor de fabricação de componentes de aviação e aeroespaciais e de defesa é um setor com produtos e serviços para finalidades específicas. Dessa forma, produtos ou serviços substitutos tendem a não ameaçar as empresas já estabelecidas do setor. No caso da AEL, que possui uma grande variedade de produtos e serviços, sua participação no setor pode não ser tão ameaçada. Haveria apenas uma concorrência com produtos semelhantes.

### Tendências

As principais tendências do setor aeroespacial e de defesa são:
- Reduzir custos de voo e emissões por meio de motores elétricos e híbridos. O cuidado com o meio ambiente tornou-se a principal questão para as empresas com um propósito, isso significa produzir motores que consumam menos combustíveis.
- Sistemas de voo autônomos. De drones a veículos, sistemas de aviação que exigem alto grau de automação.
- Ciclos de manutenção baseado em dados simulados para evitar problemas de funcionamento minimizando os custos de reparo associados.
- Fabricação de peças aditivas (produzidas a partir de um modelo digital) e consolidadas. Tendência na fabricação de peças, que reduzirão custos e tempo de montagem.
- Solução de simulação multifísica. Melhorar a previsão de reação aos sistemas de aeronaves no mundo real, principalmente minimizar riscos.

### Modelo de Negócios

A AEL Sistemas se dedica ao projeto, desenvolvimento, fabricação, manutenção e suporte logístico de avançados sistemas eletrônicos militares e espaciais, com foco nos segmentos Aeroespacial, Defesa e Segurança. Ela gera receita principalmente na venda direta de seus produtos e serviços para outras empresas, como a SAAB, Embraer e Helibras, e para as áreas do exército brasileiro (marinha, forças armadas e força aérea). Ou seja, ela não possui um distribuidor, ela vende suas soluções diretamente para seus clientes. Quando a empresa enxerga que suas soluções já existentes não são tão robustas para certo problema, ela tende a colaborar com possíveis concorrentes para desenvolver um produto mais assertivo.

## Análise do cenário: Matriz SWOT

*Matriz_SWOT*


## Proposta de Valor: Value Proposition Canvas

*Value_Proposition_Canvas*


## Matriz de Risco

*Matriz_de_risco*


# Requisitos do Sistema

*Descrição_dos_requisitos*

## Personas

*Descrição_das_personas*


## Histórias dos usuários (user stories)

*Descrição_das_histórias_dos_usuários*


# Arquitetura do Sistema

## Módulos do Sistema e Visão Geral (Big Picture)

## Descrição dos Subsistemas

### Requisitos de software


## Tecnologias Utilizadas


# UX e UI Design

## Wireframe + Storyboard

## Design de Interface - Guia de Estilos


# Projeto de Banco de Dados

## Modelo Conceitual

## Modelo Lógico


# Teste de Software

## Testes Unitários

## Teste de Usabilidade


# Análise de Dados


# Manuais

## Manual de Implantação

## Manual do Usuário

## Manual do Administrador


# Referências
