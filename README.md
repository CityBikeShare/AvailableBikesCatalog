### Bike Catalog Service

[![Build Status](https://travis-ci.org/CityBikeShare/BikeCatalogService.svg?branch=master)](https://travis-ci.org/CityBikeShare/BikeCatalogService)

- Service allows a user to publish, update or remove a bike for rent.
- Service can also: 
    - show available bikes,
    - shows bikes per region

#### Requests on kubernetes
    GET
        - 159.122.177.235:30000/sources/bikes/                    ### Get all bikes
        - 159.122.177.235:30000/sources/bikes/bike/{id}           ### Get bike by id
        - 159.122.177.235:30000/sources/bikes/region/{region}     ### Get bikes in given region
        - 159.122.177.235:30000/sources/bikes/user/{id}           ### Get bikes owned by user with given id

    PUT
        - 159.122.177.235:30000/sources/bikes/insertNew           ### Inserts new bike in the catalog [@RequestBody Bikes]
        - 159.122.177.235:30000/sources/bikes/update/{id}         ### Updated bike with given id to [@RequestBody Bikes]

    DELETE
        - 159.122.177.235:30000/sources/bikes/delete/{id}         ### Deletes a bike from the catalog