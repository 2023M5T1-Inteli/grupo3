
## Documentação da API

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
        "averageHeight": 100,
        "latitude": 49.99,
        "lastNode": {
          "low": 7,
          "high": 0
        },
        "index": {
          "low": 50,
          "high": 0
        },
        "pathID": "123",
        "longitude": 29.995
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