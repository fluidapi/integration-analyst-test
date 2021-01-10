#!/bin/bash
API_URL=http://covid-fluidapi-spring.herokuapp.com/covid-report
FILE_PATH=data/input.csv
CONTENT_TYPE="Content-Type: multipart/form-data"

curl --request POST --url ${API_URL} --header "${CONTENT_TYPE}" --form "file=@${FILE_PATH}" > result.json

if [ -x "$(command -v jq)" ];
then
  echo "jq command available"
  cat result.json | jq
elif [ -x "$(command -v docker)" ];
then
  echo "docker command available"
  docker run --rm -v $(pwd)/result.json:/tmp/result.json node:alpine \
    cat /tmp/result.json | node -e "console.log( JSON.stringify( JSON.parse(require('fs').readFileSync(0) ), 0, 1 ))"
else
  echo "jq or docker command not found"
  cat result.json
fi

rm result.json
