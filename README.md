<table>
<tr>
<td>
<a href= "https://ael.com.br/"><img src="https://www.ael.com.br/images/ael.png" alt="AEL Sistemas" border="0" width="70%"></a>
</td>
<td><a href= "https://www.inteli.edu.br/"><img src="https://www.inteli.edu.br/wp-content/uploads/2021/08/20172028/marca_1-2.png" alt="Inteli - Instituto de Tecnologia e Liderança" border="0" width="30%"></a>
</td>
</tr>
</table>

# Instruções para o projeto do Módulo 5 de Ciência da Computação

Este repositório define um modelo (*template*) que deve ser seguido por cada grupo para o projeto do módulo 5.

A seguir estão os passos sugeridos para a preparação do seu grupo no início do módulo:

* **Passo 1**: Cada membro do grupo deverá [preparar o seu computador](install.md), instalando e configurando as ferramentas necessárias para redigir a documentação, desenvolver a aplicação e elaborar o artigo.

* **Passo 2**: Cada membro do grupo deverá clonar o repositório do grupo no seu computador e [validar a preparação do seu computador para o projeto](validate.md).

* **Passo 3**: Cada grupo deverá editar este arquivo, removendo estas instruções iniciais e preenchendo o restante da página com os dados do projeto do seu grupo.


# Projeto: *Planejador de trajetórias para voos em baixa altitude*

# Grupo: *AeroGenius*

# Descrição

*Descrição_resumida_do_projeto*

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

Deverá ser publicado um release ao término de cada *sprint* do projeto.
