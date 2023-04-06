
# Documentação da API

## Guia de instalação
Vá para a página inicial do node
https://nodejs.org/en

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