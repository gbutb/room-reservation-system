# Room Reservation System 
[![Actions Status](https://github.com/gbutb/room-reservation-system/workflows/MavenTest/badge.svg)](https://github.com/gbutb/room-reservation-system/actions)

## Build instructions
`cd` to the root of the repository and execute:
```zsh
> mvn clean package
``` 
To run tests and generate coverage report use:
```zsh
> mvn clean verify
```

## Deploying
To deploy the application run the following command:
```zsh
> mvn clean spring-boot:run 
```
