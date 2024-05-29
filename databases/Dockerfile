FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root

RUN echo "CREATE DATABASE IF NOT EXISTS fithub_auth;" >> /docker-entrypoint-initdb.d/create_databases.sql && \
    echo "CREATE DATABASE IF NOT EXISTS fithub_chat;" >> /docker-entrypoint-initdb.d/create_databases.sql && \
    echo "CREATE DATABASE IF NOT EXISTS fithub_membership;" >> /docker-entrypoint-initdb.d/create_databases.sql && \
    echo "CREATE DATABASE IF NOT EXISTS fithub_training;" >> /docker-entrypoint-initdb.d/create_databases.sql && \
    echo "CREATE DATABASE IF NOT EXISTS fithub_mealplan;" >> /docker-entrypoint-initdb.d/create_databases.sql

EXPOSE 3306

HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD mysqladmin ping -h localhost -p${MYSQL_ROOT_PASSWORD} || exit 1