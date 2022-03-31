#!/bin/bash

rm -rf src/api
npx @openapitools/openapi-generator-cli generate \
   -i http://localhost:8080/v3/api-docs \
   -g typescript-fetch \
   -o ./api
