<table>
<tr>
<td>
<a href= "https://ael.com.br/"><img src="https://www.ael.com.br/images/ael.png" alt="AEL Sistemas" border="0" width="70%"></a>
</td>
<td><a href= "https://www.inteli.edu.br/"><img src="https://www.inteli.edu.br/wp-content/uploads/2021/08/20172028/marca_1-2.png" alt="Inteli - Instituto de Tecnologia e Liderança" border="0" width="30%"></a>
</td>
</tr>
</table>

# Projeto: *FlightWise: Planejador de trajetórias para voos em baixa altitude*

# Grupo: *AeroGenius*

# Descrição

Diversas aplicações dentro da indústria de aviação, como busca e salvamento, por exemplo, se beneficiam de realizar voos a baixa altitude. No entanto, em decorrência da proximidade com o solo esse tipo de operação representa um risco iminente de colisão com o solo. Tendo em vista essa problemática, a AEL Sistemas desenvolve sistemas de Terrain Following embarcados em plataformas aéreas com foco em sistemas críticos de missão. Sistemas de Terrain Following são utilizados para auxiliar esse tipo de missão provendo uma guiagem ao piloto, orientando ele como conduzir a aeronave de forma segura nesse ambiente desafiador a partir de dados obtidos do voo.

O problema que a solução pretende resolver está relacionado à geração e otimização do trajeto de voos de baixa altitude considerando os riscos de colisão com o solo, fatores que possam afetar a missão e a distância do voo. Atualmente, as trajetórias de voo construídas são levadas a evitar áreas mais povoadas pela maior probabilidade de ter sistemas de monitoramento. Diante disso, ao traçar a trajetória, áreas com feições geográficas elevadas como morros, vales e cadeias de montanhas, que minimizam ainda mais a probabilidade de detecção, tendem a ser mais escolhidas, o que gera uma necessidade ainda maior de controle da rota para a segurança dos tripulantes.

A solução consiste em um software desenvolvido em Java capaz de gerar a melhor trajetória de acordo com a altitude e distância entre a origem e o destino. O usuário pode inserir as coordenadas do ponto de origem e do ponto de destino, e possíveis áreas de exclusão (digitando as coordenadas do centro do círculo e seu raio). Como saída é gerada uma série de nós que conectam os pontos de partida e de destino respeitando as restrições impostas na especificação do usuário em cima de um mapa que representa a área da missão.

# Documentação

Os arquivos da documentação deste projeto estão na pasta [/docs](/docs), e o seu conteúdo é publicado em https://2023m5t1-inteli.github.io/grupo3.

## Documentação da API 

### Spring Boot (Java)


```http
POST /executeAlg
``` 
Esta rota executa toda a "pipeline" necessária para que seja criado um Grafo a partir dos arquivos e seja calculado a melhor rota possível, baseado no algoritmo A*, tendo a menor altitude possível. 

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `lonInitial` | `double` | Longitude inicial (saída) |
| `latInitial` | `double` | Latitude inicial (saída) | 
| `lonFinal` | `double` | Longitude final (destino) |
| `latFinal` | `double` | Latitude final (destino) |
| `exclusionPoints`| `double Array` | Pontos de exclusão, segue a ordem: [[latitude1, longitude1, raio1], [latitude2, longitude2, raio2], ...[] |
| `pathID` | `string` | ID para identificar esta rota |
| `filePath` | `string` | Nome do arquivo a ser lido |

##### Expected response
```JSON
"Rota criada com sucesso!"
```

##### Error response
```JSON
"Erro ao criar rota!"
```

``` http
POST /getMapBounds
```
Esta retorna o ponto de latitude e longitude máximo e minímo de um arquivo DT2. Uma vez que este arquivo representa geométricamente um quadrilátero, o ponto mínimo se encontra no canto superior esquerdo e o máximo em seu canto inferior direito. 
| Parâmetro | Tipo | Descrição | 
| :---------- | :--------- | :---------------------------------- |
| `filePath` | `string` | Nome do arquivo a ser lido |

##### Expected response
``` JSON 
"Exemplo do Arquivo do Rio de Janeiro"
{
	"minLon": -44.00013888888889,
	"maxLat": -21.99986111111111,
	"minLat": -23.99986111111111,
	"maxLon": -42.00013888888889
}
```


``` http
POST /listAllBounds
```
Esta retorna o ponto de latitude e longitude máximo e minímo de todos os arquivos DT2. A lógica é semelhante a da rota anterior. 
| Parâmetro | Tipo | Descrição | 
| :---------- | :--------- | :---------------------------------- |
| `filePath` | `string` | Nome do arquivo a ser lido |

##### Error response
``` JSON 
"Erro ao obter os limites do mapa!"
```

### Como executar este microsserviço?
Devida a alta complexidade deste projeto, é necessário diversas dependências para sua execução. Elas podem ser fácilmente acessadas e encontradas no arquivo [pom.xml](/pom.xml). Contudo, ainda há muitas ferramentas que devem ser instaladas e configuradas antes da execução deste projeto. Abaixo está listadas as principais.

#### 1. GDAL 
O GDAL é a ferramenta utilizada para que os arquivos dt2 sejam lidos através da nossa aplicação Java. Para instalá-lo, siga os passos abaixo: 

1. Acesse https://build2.gisinternals.com/sdk/downloads/release-1916-x64-gdal-3-5-0-mapserver-7-6-4/gdal-305-1916-x64-core.msi. Após o término do download, execute a instalação (selecione a instalação completa).

2. Acesse https://build2.gisinternals.com/sdk/downloads/release-1916-x64-gdal-3-5-0-mapserver-7-6-4/gdal-305-1916-x64-filegdb.msi. Após o término do download, execute a instalação (selecione a instalação completa).

3. Crie variáveis de ambiente com os seguintes valores:


| Variável | Valor |
|----------|-------|
| GDAL_DATA | C:\Program Files\GDAL\gdal-data |
| GDAL_DRIVER_PATH | C:\Program Files\GDAL\gdalplugins |
| PROJ_LIB | C:\Program Files\GDAL\projlib |

4. Adicione o seguinte caminho ao `PATH`:

```
    C:\Program Files\GDAL
```

Vá até a pasta `C:\Program Files\GDAL\gdalplugins\` e mova para outra pasta (por exemplo, para `C:\Temp`) o arquivo `ogr_MSSQLSpatial.dll`.

#### 2. JAVA

Graças a sua alta performance e solidez utilizamos o Java como linguagem para manipular diferentes arquivos dt2 e aplicar nosso algoritmo A*. Você pode instalar o Java através [desse link](https://www.oracle.com/java/technologies/downloads/), contudo, para executar a aplicação com êxito recomendamos que utilize a versão 17 ou superior. 

#### 3. IntelliJ

Para executar este programa recomendamos o auxílio de um IDE, que irá facilitar a instalação das dependências de forma automática. Dentre as diferentes IDEs do mercado recomendamos o [IntelliJ](https://www.jetbrains.com/idea/) graças a sua compatibilidade e integração com o [Maven](https://maven.apache.org/) e os diferentes recursos que possui.

#### 4. Spring Boot e outras bibliotecas 

Durante a criação da aplicação foi utilizada diferentes dependências que como citado anteriormente. Elas podem ser encontradas no arquivo pom.xml do projeto, e ao ser aberto no IntelliJ — uma vez que todas as ferramentas anteriores foram instaladas — as bibliotecas serão instaladas automaticamente através do Maven. Abaixo temos as principais bibliotecas para executar o projeto com êxito.

A. [Neo4J Driver](https://neo4j.com/developer/java/) B. [Spring Boot](https://spring.io/) C. [JSON](https://mvnrepository.com/artifact/org.json/json) D. [MongoDB](https://www.mongodb.com/compatibility/spring-boot) E. [GDAL](https://mvnrepository.com/artifact/org.gdal/gdal)
 
 ### 5. Variáveis de ambiente
 
 Os únicos serviços que necessessitam de chaves de acesso são nossos bancos de dados. Utilizamos dois bancos de dados para o projeto: [Neo4J](https://sandbox.neo4j.com/) (Banco de dados orientado a grafos) e o [MongoDB]. Você encontrará mais sobre as motivações e a persistência dos dados na próxima seção. Para utilizarmos o Neo4J é necessário que se crie uma instância no [Neo4J Aura](https://neo4j.com/cloud/platform/aura-graph-database/); uma vez que temos uma instância do banco, criamos um arquivo ```.env``` na raiz do nosso projeto (pasta inicial). Recomendamos que utilize nossa referência de arquivo .env encontrado em [.env-example](/.env-example), e preencha com as informações de acesso de sua instância do Neo4J Aura. 

Para termos controle das rotas, é necessário que você crie um cluster no MongoDB e preencha o arquivo [application.properties](/src/main/resources/application.properties) com sua chave de acesso e nome do banco de dados.

# Persistência dos Dados

Como citado anteriormente, utilizamos dois bancos de dados diferentes. Uma vez configurado o ambiente deles, ainda é necessário compreender a maneira que a aplicação os utiliza. O Neo4J é uma ferramenta poderossíma e recomendamos que você veja o curso, através [desse link](https://graphacademy.neo4j.com/). O Neo4J utiliza a mesma estrutura de vértices e arestas que o grafo gerado dentro da aplicação e que pode ser facilmente compreendido através de sua documentação própria. Em resumo, cada vértice representa uma coordenada geográfica e conta com as seguintes características: latitude, longitude, altura média, index e pathID (para que seja reconhecível de qual rota este ponto pertence). Cada aresta por sua vez, representa a conexão entre duas coordenadas e possui apenas um id gerado automaticamente. 

Contudo, o MongoDB apresenta uma estrutura de dados fixa que deve ser respeitada. Uma vez criado sua conta (como citado no passo 5 da seção anterior) você deve certificar-se de que há um cluster disponível, que pode ser criado na tela inicial de forma gratuita ou aderindo a alguns dos planos disponíveis. Após isso, você deve criar um banco de dados. O nome pode ficar a seu critério, contudo, lembre-se de alterar no arquivo [application.properties](/src/main/resources/application.properties).

![gif](https://github.com/2023M5T1-Inteli/grupo3/blob/master/docs/img/MongoDB.gif)

Como podemos ver no GIF acima, é necessário também especificar o nome de uma coleção. Nossa aplicação utiliza a coleção por padrão "routes" e recomendamos que escreva esse nome ao criar o banco de dados. Caso não crie uma coleção com este nome, ao executar a aplicação outra coleção denominada "routes" será criada de forma automática. No entanto, caso queira utilizar uma outra coleção, troque a linha 19 do arquivo [RouteItem.java](/src/main/java/controller/mongodbModel/RouteItem.java/) de "routes" para o nome desejado, desde que respeite a seguinte modelagem: 
```JSON
{
  "_id": ObjectID,
  "routeID": string,
  "status": string,
  "_class":"controller.model.RouteItem"
  
}
```
# Artigo

Os arquivos do artigo estão na pasta [/artigo](/artigo). Um arquivo gerado no formato PDF deverá ser anexado a cada *release* do projeto.

# Releases

É possível ver os Realeases de cada sprint no menu lateral.
