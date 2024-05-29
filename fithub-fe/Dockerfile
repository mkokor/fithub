FROM node:14 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

FROM node:14 AS development
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 3000
ENTRYPOINT ["/app/wait-for-it.sh", "api-gateway:8080", "--", "npm", "start"]