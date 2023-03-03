---
title: Modelo para o artigo do Módulo 5
author: Professores do Módulo 5 - Inteli
date: Janeiro de 2023
abstract: Como parte das atividades do módulo 5, cada grupo deverá redigir um texto descrevendo os resultados do projeto no formato de um artigo científico. Este arquivo no formato markdown contém a estrutura básica deste artigo. Cada grupo deverá editar este arquivo com a descrição do projeto que desenvolveu.
---

# Introdução
Voos em baixa altitude, principalmente em áreas montanhosas, é considerado uma tarefa complexa utilizada em diversas aplicações. Evitar detecções inimigas e melhorar as chances de sobrevivência são os principais propóstios no setor militar. Além disso, no setor civil é usada para reconhecimento de baixo nível, entrega de material em locais remotos, busca e resgate, e evacuação de vítimas (JIN et al., 2022).  Além disso, serve em situações de terremotos, em que é necessário se aproximar das construções (QI et al., 2016), ou na estimativa de rendimento e precisão na agricultura (REZA et al., 2019). Dessa forma, tornar esses voos mais seguros e otimizados é essencial para que tais propósitos sejam atendidos de forma rápida e da melhor forma possível.

Entretanto, nesse tipo de voo o risco iminente de colisão com o solo (CFIT) é alto. Acidentes de CFIT ocorrem quando uma aeronave, sob o controle de um piloto, atinge involuntariamente um terreno, água ou obstáculos (MATTESON, 2001). Em voos comerciais, mais de 30% dos acidentes fatais são categorizados como CFIT (ARTHUR III, 2003). Assim, no setor militar é imprescindível a construção de uma trajetória de voo que utiliza o terreno ao seu favor para evitar detecções, evadindo sistemas de monitoramento, e ao mesmo tempo reduz as chances de colisão. Para tentar amenizar esse problema allgumas soluções já são desenvolvidas com a finalidade de garantir maior segurança ao piloto.

Uma delas é o Terrain Following. Seu principal objetivo é manter uma distância mínima definida acima do terreno e voar rapidamente, de modo a minimizar os riscos de ser detectado e rastreado (Lu e Pierson, 1995). Ao buscar cobertura no solo para evitar detecção, o terreno acidentado e perigoso, juntamente com o objetivo de voar em alta velocidade próximo ao solo, se traduz em um problema de controle altamente restrito. Mesmo o mapa de maior resolução pode não ser capaz de avisar sobre obstáculos inesperados. Essas informações só podem ser obtidas em tempo real por meio de varreduras de sensores durante o voo. Isso destaca a necessidade de um algoritmo eficiente que possa incorporar novos conhecimentos de obstáculos obtidos pelos sensores na otimização da trajetória em tempo real, permitindo o um replanejamento dinâmico. 

Além disso, devido à proximidade do veículo com muitas restrições variáveis, manobras ágeis são necessárias, o que aumenta o risco de colisão(LAPP, 2004). Consequentemente, o sistema deve levar em consideração diversos aspectos como o envelope de voo da aeronave específica, seu potencial de subida (Climb rate [ft/s]), a manobrabilidade lateral da aeronave (Turn rate [deg/sec]), velocidade operacional, teto de operação, assim como aspectos geográficos da região em questão.

Neste artigo, nós propomos uma metodologia que se baseia no uso de entradas, como os dados da região geográfica de operação, o banco de dados de elevação, as zonas de exclusão (restrições) e o ponto de partida e de destino, para a construção de um grafo incompleto e direcionado que representa a trajetória mais otimizada para tal percurso. Buscando-se obter o caminho mais otimizado, será priorizada, pelo algoritmo, a rota que suprir os requisitos e parâmetros de entrada, devendo esta, não atingir pontos de exclusão, respeitar os limites de voo da aeronave, como velocidade máxima, raio de curvatura e outros, além de buscar diminuir o consumo de combustível e distância entre o ponto inicial e final, passando por localizações pré-definidas, caso estas sejam especificadas.

Os principais benefícios de nossa solução são uma visão completa do terreno e da rota, melhoria do consumo de combustível, redução de custos, economia de recursos e otimização do tempo na elaboração das rotas. Além disso, poderá ser integrada com produtos existentes que visam amenizar o atual problema. Dessa forma, o presente artigo irá contribuir com a apresentação de um modelo capaz de tornar as missões de voo em baixa altitude mais seguras e otimizadas, evitando acidentes de CFIT, gerando um maior sucesso das mesmas, e consequentemente, garantindo os propósitos de voos em baixa altitude.

# Descrição do problema

# Trabalhos relacionados

Com o avanço da tecnologia, os helicópteros autônomos têm se mostrado uma alternativa promissora para operações de baixa altitude em terrenos montanhosos. A partir disso, foi proposto um framework de controle autônomo de operações de helicópteros não tripulados em voo de baixa altitude em terrenos montanhosos. O objetivo é permitir que o helicóptero execute operações de forma autônoma, sem a intervenção humana constante. O framework inclui 3 módulos principais: percepção, tomada de decisão e controle. O módulo de percepção utiliza sensores para coletar informações do ambiente, enquanto o módulo de tomada de decisão utiliza um algoritmo de planejamento de trajetória para determinar a melhor rota para o helicóptero. O módulo de controle utiliza um controlador PID para garantir que o helicóptero siga a trajetória planejada e evite obstáculos. O Framework permite que o helicóptero execute operações de forma autônoma em terrenos montanhosos, o que pode ser útil em tarefas como inspeção de linhas de energia e monitoramento de áreas remotas. No entanto, O framework não leva em oonsideração a interação do helicóptero com outras aeronaves ou com humanos, o que pode ser um problema em áreas com alto tráfego aéreo ou densidade populacional.

O artigo "Search and rescue rotary‐wing uav and its application to the lushan ms 7.0 earthquake" apresenta um sistema de busca e resgate com um helicóptero não tripulado (UAV) de asa rotativa e sua aplicação no terremoto de magnitude 7.0 em Lushan, China, em 2013 (QI et al,2016). O sistema é composto por um UAV, estação de controle terrestre e um sistema de visão em tempo real. O UAV é capaz de voar em ambientes complexos e coletar informações em tempo real por meio de câmeras e sensores. A estação de controle terrestre permite que os operadores controlem o UAV e recebam informações em tempo real. O sistema de visão em tempo real é capaz de fornecer informações detalhadas sobre a área de busca e resgate, permitindo que a equipe de resgate tome decisões informadas. O sistema foi testado em uma missão de busca e resgate no terremoto de Lushan, e os resultados mostraram que o UAV foi capaz de localizar vítimas em áreas de difícil acesso e fornecer informações úteis para a equipe de resgate. O artigo conclui que o sistema de busca e resgate com UAVs pode ser uma solução eficaz para situações de desastres naturais. Os resultados dos testes mostraram que o sistema foi capaz de localizar vítimas em áreas de difícil acesso e fornecer informações úteis para a equipe de resgate. Todavia, o custo de implementação do sistema pode ser alto, uma vez que requer UAVs, sensores e equipamentos especializados.

Em "Rice yield estimation based on K-means clustering with graph-cut segmentation using low-altitude UAV images"(REZA et al., 2019), o autor propõe um método para estimar o rendimento do arroz usando imagens de UAV (veículos aéreos não tripulados) obtidas a baixa altitude. O método é baseado em dois processos principais: segmentação e classificação. A segmentação é realizada usando um algoritmo de corte de gráficos para separar as plantas do fundo. A classificação é feita usando o algoritmo de clusterização K-means para identificar diferentes estágios de crescimento das plantas de arroz. O rendimento é estimado usando uma equação que relaciona o número de plantas por metro quadrado com o rendimento por hectare. Os resultados mostram que o método proposto é capaz de estimar o rendimento do arroz com alta precisão e pode ser uma ferramenta útil para os agricultores e pesquisadores na tomada de decisões em relação ao cultivo de arroz. O uso de UAVs  pode trazer vantagens em termos de custo e tempo, já que a coleta de dados pode ser feita de forma mais rápida e eficiente do que as medições manuais. No entanto, Os algoritmos utilizados podem exigir habilidades técnicas avançadas para implementação e ajuste, o que pode dificultar a replicação da técnica por outros pesquisadores ou produtores rurais.

"Controlled Flight into Terrain: How the Airlines and the Federal Aviation Administration are Addressing the Problem" (MATTESON, 2001) discute a questão de voos controlados contra o terreno (CFIT, na sigla em inglês), que ocorrem quando uma aeronave em pleno voo colide com o solo ou outras obstruções devido a erros humanos ou falhas técnicas. O autor apresenta estatísticas sobre o número de acidentes desse tipo e destaca a importância de se tomar medidas para prevenir futuras ocorrências. O autor também enfatiza a importância de tecnologias avançadas e treinamento adequado dos pilotos para garantir a segurança das operações aéreas.

O tema da segurança em operações aéreas é crucial e tem sido objeto de estudos e aprimoramentos constantes ao longo dos anos. O artigo "CFIT prevention using synthetic vision" (ARTHUR III, 2001) destaca a gravidade dos acidentes CFIT e a importância das iniciativas da FAA e das companhias aéreas para prevenir futuras ocorrências. O autor enfatiza a importância de tecnologias avançadas e treinamento adequado dos pilotos para garantir a segurança das operações aéreas. Como limitações do estudo, podemos citar, por exemplo, a concentração exclusiva em uma abordagem específica para prevenir o voo controlado para o solo (CFIT) usando visão sintética, sem considerar outros fatores que podem contribuir para o problema. 

Ping Lu e Bion L. Pierson publicaram um estudo no Journal of Guidance, Control, and Dynamics que descreve uma metodologia para gerar trajetórias ótimas em aviões militares de asa fixa, visando um voo de seguimento de terreno. O artigo descreve a modelagem matemática do problema, considerando a dinâmica do avião e as limitações de segurança, bem como o terreno e as características ambientais. O algoritmo proposto para gerar as trajetórias é baseado em programação dinâmica, com o objetivo de minimizar o consumo de combustível, atender aos requisitos de segurança e manter a aeronave a uma altitude segura em relação ao terreno. O artigo apresenta resultados de simulações para validar a metodologia proposta e demonstra que as trajetórias geradas são efetivas e seguras. O trabalho contribui para a área de navegação de aviões militares e pode ter aplicações em outras áreas, como aeronaves civis e sistemas de controle de tráfego aéreo. Um dos pontos positivos do artigo é que o estudo apresenta resultados de simulações que demonstram a efetividade e segurança das trajetórias geradas pela metodologia proposta, o que fornece evidências empíricas da viabilidade da abordagem.

O segmento de terrenos em voos de baixa altitude é um desafio constante na aviação. Para evitar colisões, é fundamental manter uma distância segura em relação ao terreno. Nesse contexto, a tese de doutorado LAPP, de Tiffany Rae, desenvolvida no Massachusetts Institute of Technology (MIT), propõe uma técnica inovadora de controle de voo baseada no controle preditivo de modelos. O objetivo é ajustar continuamente o voo com base em leituras do terreno, considerando diversos fatores, como a dinâmica do vento, altitude, velocidade, aceleração e ângulo de inclinação da aeronave. Essa técnica tem o potencial de melhorar significativamente a segurança e precisão da navegação em voos de baixa altitude.

# Descrição da estratégia adotada para resolver o problema

# Análise da complexidade da solução proposta

Neste artigo, cada grupo precisará fazer a análise de complexidade da solução proposta, utilizando as notações $O(.)$, $\Omega(.)$ e $\Theta(.)$.

A seguir temos a citação de alguns trechos de DASGUPTA et. al. (2011) para mostrar como estas notações são em \LaTeX. 

> Sejam $f(n)$ e $g(n)$ duas funções de inteiros positivos em reais positivos. Dizemos que $f = O(g)$ (que significa que "$f$ não cresce mais rápido do que $g$") se existe uma constante $c > 0$ tal que $f(n) \leq c \cdot g(n)$.

Ainda em outro trecho de DASGUPTA et. al. (2011), temos:

> Assim como $O(.)$ é análogo a $\leq$, podemos definir análogos de $\geq$ e $=$ como se segue:

> $f = \Omega(g)$ significa $g = O(f)$

# Análise da corretude da solução proposta

# Resultados obtidos

# Conclusão

# Referências Bibliográficas

JIN, Zibo et al. An autonomous control framework of unmanned helicopter operations for low-altitude flight in mountainous terrains. Drones, v. 6, n. 6, p. 150, 2022.

QI, Juntong et al. Search and rescue rotary‐wing uav and its application to the lushan ms 7.0 earthquake. Journal of Field Robotics, v. 33, n. 3, p. 290-321, 2016.

REZA, Md Nasim et al. Rice yield estimation based on K-means clustering with graph-cut segmentation using low-altitude UAV images. Biosystems engineering, v. 177, p. 109-121, 2019.

MATTESON, Roger C. Controlled Flight into Terrain: How the Airlines and the Federal Aviation Administration are Addressing the Problem. Journal of Aviation/Aerospace Education & Research, v. 10, n. 3, p. 4, 2001.

ARTHUR III, Jarvis J. et al. CFIT prevention using synthetic vision. In: Enhanced and Synthetic Vision 2003. SPIE, 2003. p. 146-157.

LU, Ping; PIERSON, Bion L. Optimal aircraft terrain-following analysis and trajectory generation. Journal of guidance, Control, and Dynamics, v. 18, n. 3, p. 555-560, 1995.

LAPP, Tiffany Rae. Guidance and control using model predictive control for low altitude real-time terrain following flight. 2004. Tese de Doutorado. Massachusetts Institute of Technology.

DASGUPTA, S.; Papadimitriou, C.; Vazirani, U. **Algoritmos.** Porto Alegre: AMGH, 2011. 1 recurso online. ISBN 9788563308535. Disponível em: https://integrada.minhabiblioteca.com.br/books/9788563308535. Acesso em: 17 jan. 2023.