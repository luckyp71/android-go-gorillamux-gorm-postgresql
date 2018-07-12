FROM golang:latest
RUN mkdir /go-gorilla-postgresql
ADD . /go-gorilla-postgresql/
WORKDIR ./go-gorilla-postgresql/controller/
RUN go build -o main .
CMD ["/go-gorilla-postgresql/controller"]

EXPOSE 8090
