FROM node:16.14 as build
WORKDIR /app

RUN npm install -g @angular/cli

COPY ./parking-system-frontend/package.json .
RUN npm install

COPY parking-system-frontend .
RUN ng build

FROM nginx as runtime
COPY --from=build /app/dist/parking-system-frontend /usr/share/nginx/html