# shopping-cart
Shopping cart example

[![CircleCI](https://circleci.com/gh/yigithanbalci/shopping-cart.svg?style=svg)](https://circleci.com/gh/yigithanbalci/shopping-cart)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=shopping-cart&metric=coverage)](https://sonarcloud.io/dashboard?id=shopping-cart)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=shopping-cart&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=shopping-cart)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=shopping-cart&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=shopping-cart)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=shopping-cart&metric=security_rating)](https://sonarcloud.io/dashboard?id=shopping-cart)


## run
```
mvn spring-boot run
```
## swagger docs
```
http://localhost:8080/swagger-ui.html
```

## AWS Deployment with ECS, ECR and EC2 loadbalancer
```
terraform init
terraform apply
docker build -t shopping-cart-ecr-repo .
docker tag shopping-cart-ecr-repo:latest your-ecr-link.amazonaws.com/shopping-cart-ecr-repo:latest
docker push your-ecr-link.amazonaws.com/shopping-cart-ecr-repo:latest
```

### Users for authentication and authorizatioon

| Username | Password | Role |
| -----------| ------ | ------ |
| admin | admin| ADMIN | 
| user1 | user1 | USER | 
| user2 | user2 | USER | 
| user3 | user3 | USER | 
| user4 | user4 | USER | 

### Http Status
- 200 OK: The request has succeeded
- 201 Created: The request has created
- 401 UnAuthorized: The request lacks valid authentication credentials for the target resource 
- 403 Forbidden: The server understood the request but refuses to authorize it. 
- 404 Not Found: The requested resource cannot be found
- 500 Internal Server Error: The server encountered an unexpected condition 


## Sample Jsons
___

### item
```
{
  "drink": {
    "name": "Latte",
    "price": 6.0
  },
  "toppings": [
    {
      "name": "Milk",
      "price": 2.0
    }
  ],
  "amount": 8.0
}
```
### cart
```
{
  "items": [
    {
      "drink": {
        "name": "Black Coffee",
        "price": 5.0
      },
      "toppings": [
        {
          "name": "Maple Syrup",
          "price": 2.0
        }
      ],
      "amount": 2.0
    }
  ],
  "amount": 7.0
}
```
### topping
```
{
  "name": "Lemon",
  "price": 1.0
}
```

### example requests via curl

```
POST DRINK
curl -u admin:admin -X POST "http://localhost:8080/admin/products/drinks" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"name\":\"Americano\",\"price\":5.0}"

DELETE TOPPING
curl -u admin:admin -X DELETE "http://localhost:8080/admin/products/toppings/1" -H "accept: */*"

GET TOPPINGS
curl -u user1:user1 -X GET "http://localhost:8080/products/toppings" -H "accept: application/json"

GET TOTAL ORDERS
curl -u admin:admin -X GET "http://localhost:8080/admin/reports/users/total-orders" -H "accept: application/json"

PUT ITEM
curl -u user2:user2 -X PUT "http://localhost:8080/users/2/cart" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"drink\":{\"name\":\"Latte\",\"price\":5.0},\"toppings\":[{\"name\":\"Lemon\",\"price\":1.0}],\"amount\":6.0}"

PUT CART (CHECKOUT CART)
curl -u user1:user1 -X PUT "http://localhost:8080/users/1/cart/checkout" -H "accept: application/json"

```
