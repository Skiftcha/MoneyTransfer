# MoneyTransfer test application

## To build and run unit tests
```
./gradlew build
```

## To run application
```
./gradlew run
```
Runs application on 8080 port

## To run intergation tests while application is running
```
./gradlew integrationTest
```
Runs integration tests accessing api via 8080 port

## API Resources

  - [GET /accounts](#get-accounts)
  - [GET /accounts/history](#get-accountshistory)
  - [POST /accounts/create](#post-accountscreate)
  - [GET /accounts/[id]](#get-accountsid)
  - [PUT /accounts/deposit](#put-accountsdeposit)
  - [PUT /accounts/withdraw](#put-accountswithdraw)
  - [PUT /accounts/transfer](#put-accountstransfer)

### GET /accounts

Returns list of accounts and their balance

##### Request body: no request body

##### Response body:

    [
        {
            "id": 1,
            "balance": 5
        },
        {
            "id": 2,
            "balance": 333
        }
    ]

##### Example:

    curl "http://localhost:8080/accounts"

### GET /accounts/history

Returns money transfer history
<br>
```null``` value in ```from``` or ```to``` field means it is deposit or withdrawal operation respectively

##### Request body: no request body

##### Response body:

    [
        {
            "from": null,
            "to": 1,
            "amount": 10,
            "time": 1539565845785
        },
        {
            "from": 1,
            "to": null,
            "amount": 5,
            "time": 1539565845793
        },
        {
            "from": 6,
            "to": 7,
            "amount": 7,
            "time": 1539565845982
        }
    ]

##### Example:
    
    curl "http://localhost:8080/accounts/history"

### POST /accounts/create

Creates new account and returns its id

##### Request body: no request body

##### Response body:

    13

##### Example:
    
    curl -X POST "http://localhost:8080/accounts/create"

### GET /accounts/[id]

Returns account balance
<br>
Returns 404 Not Found error if no account found

##### Request body: no request body

##### Response body:

    444

##### Example:
    
    curl "http://localhost:8080/accounts/3"

### PUT /accounts/deposit

Increases account balance
<br>
Returns 404 Not Found error if no account found

##### Request body:

    {
        "id": 2,
        "amount": 10
    }

##### Response body: no response body

##### Example:
    
    curl -X PUT -H "Content-Type: application/json" "http://localhost:8080/accounts/deposit" -d '{"id": 2, "amount": 10}'

### PUT /accounts/withdraw

Decreases account balance
<br>
Returns 404 Not Found error if no account found
<br>
Returns 400 Bad Request error if not enough money for withdrawal

##### Request body:

    {
        "id": 2,
        "amount": 10
    }

##### Response body: no response body

##### Example:
    
    curl -X PUT -H "Content-Type: application/json" "http://localhost:8080/accounts/withdraw" -d '{"id": 2, "amount": 10}'

### PUT /accounts/transfer

Transfers balance between accounts
<br>
Returns 404 Not Found error if no account found
<br>
Returns 400 Bad Request error if not enough money for transfer

##### Request body:

    {
        "from": 2,
        "to": 3,
        "amount": 10
    }

##### Response body: no response body

##### Example:
    
    curl -X PUT -H "Content-Type: application/json" "http://localhost:8080/accounts/transfer" -d '{"from": 2, "to": 3, "amount": 10}'
