![example workflow](https://github.com/MLGMAG/contacts/actions/workflows/.github-ci.yml/badge.svg)

### Database setup
```
docker run --name postgresDB \
-p 8002:5432 \
--restart always \
-e POSTGRES_PASSWORD=postgres \
-d postgres
```

### Run app

```
docker run -d \
--name contactsApp \
-e DB_HOST=db_ip \
-e DB_PORT=db_port \
-e DB_NAME=db_name \
-e DB_USERNAME=db_username \
-e DB_PASSWORD=db_password \
-e SERVER_PORT="8090" \
mlgmag/contacts:latest
```
