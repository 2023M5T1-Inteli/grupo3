
# Documentação da API

## Guia de desenvolvimento
Para rodar o backend da aplicação, siga os seguintes passos:

1. Certifique-se de ter o Node.js instalado em sua máquina. Você pode verificar se o Node.js está instalado executando o seguinte comando no terminal:
```
node -v
```
Se o Node.js não estiver instalado, faça o download da versão v18.0.0 em https://nodejs.org.

2. Navegue até o diretório "backend" no terminal e instale as dependências do projeto usando o gerenciador de pacotes NPM (Node Package Manager):
```
npm install
```
3. Crie um arquivo .env na raiz do diretório "backend" e adicione as seguintes variáveis de ambiente, com os valores de conexão do Neo4j e do MongoDB:
```env
# Neo4j
NEO4J_URL = 
NEO4J_USERNAME =
NEO4J_PASSWORD =

# MongoDB
MONGODB_URL =
```

4. Inicie o servidor de desenvolvimento do backend executando o seguinte comando:
```
npm start
```

5. Agora, você pode acessar a API em http://localhost:4000.

## Rotas
### Retorna a rota final

```http
  GET /graph
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `pathID` | `string` | **Obrigatório** |

##### Expected response
```javascript
    [
      {
        "vertexIndex": 0,
        "averageHeight": 572,
        "previousVertex": -1,
        "latitude": -22.952082,
        "longitude": -43.211056
      },
      {
        "vertexIndex": 24,
        "averageHeight": 316,
        "previousVertex": 0,
        "latitude": -22.953482,
        "longitude": -43.209956
      }
    ]
```

### Retorna um item

```http
  GET /graph/checkRouteStatus
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `routeID`      | `string` | **Obrigatório** |

##### Expected response
```javascript
    {
      "routeID": "VNB018W",
      "status": "Creating"
    }
```

### Solicita a criação da rota e retorna o código da rota
```http
  POST /graph
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `entryPoints` | `array` | **Obrigatório** |
| `exitPoints` | `array` | **Obrigatório** |
| `exclusionPoints` | `array` | **Obrigatório** |
| `intermediatePoints` | `array` | **Obrigatório** |

##### Expected response
```javascript
    {
      "routeID": "VNB018W"
    }
```

### Swagger

A documentação também foi feita com o uso do Swagger, uma especificação de linguagem agnóstica que descreve as interfaces de programação de aplicativos RESTful. Para visualizar o código em openAPI, acesse o link https://shre.ink/kjzx. Caso queira visualizar a documentação que foi gerada, clique no botão “view documentation”, no canto superior direito.