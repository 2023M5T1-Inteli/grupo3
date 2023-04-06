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
# Artigo

Os arquivos do artigo estão na pasta [/artigo](/artigo). Um arquivo gerado no formato PDF deverá ser anexado a cada *release* do projeto.

# Releases

É possível ver os Realeases de cada sprint no menu lateral.
