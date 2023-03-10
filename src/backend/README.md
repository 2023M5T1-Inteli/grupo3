
## Documentação da API

### Retorna a rota final

```http
  GET http://localhost:4000/graph
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
  GET http://localhost:3000/graph/checkRouteStatus
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
  POST http://localhost:3000/graph
```
##### Expected response
```javascript
    {
      "routeID": "VNB018W"
    }

```

