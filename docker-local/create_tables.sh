#bin/bash

# Create the tables in the database

IS_LOCAL="false"


# shellcheck disable=SC2034
if IS_LOCAL=true; then
    # shellcheck disable=SC2089
    COMMAND='aws dynamodb create-table --endpoint-url=http://localhost:8000'
else
    COMMAND='aws dynamodb create-table '
fi
for file in ./tables/*.json; do
    $COMMAND --cli-input-json file://$file
done

