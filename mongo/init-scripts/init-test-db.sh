mongo -u mongo \
      -p mongo \
      --eval "var DB_DBNAME='test'; var DB_USERNAME='test'; var DB_PASSWORD='test';" \
      --verbose \
      admin \
      /mongo-js-scripts/helpers.js \
      /mongo-js-scripts/create_test_db.js
