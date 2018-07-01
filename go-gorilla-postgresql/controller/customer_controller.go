package main

import (
	"encoding/json"
	"fmt"
	"net/http"

	m "go-gorilla-postgresql/model"

	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

var db *gorm.DB
var e error

func main() {
	db, e = gorm.Open("postgres", "user=postgres password=pratama dbname=postgres sslmode=disable")
	if e != nil {
		fmt.Println(e)
	} else {
		fmt.Println("Connection Established")
	}
	defer db.Close()
	db.SingularTable(true)
	db.AutoMigrate(m.Customer{})

	router := mux.NewRouter()
	router.HandleFunc("/customers/", getCustomers).Methods("GET")
	router.HandleFunc("/customers/{name}/list", getCustomersByName).Methods("GET")
	router.HandleFunc("/customers/{id}", getCustomerByID).Methods("GET")
	router.HandleFunc("/customers", insertCustomer).Methods("POST")
	router.HandleFunc("/customers/{id}", updateCustomerByID).Methods("PUT")
	router.HandleFunc("/customers/{id}", deleteCustomer).Methods("DELETE")
	http.ListenAndServe(":8090", router)
}

// Get customers
func getCustomers(w http.ResponseWriter, r *http.Request) {
	var customers []m.Customer
	if e := db.Find(&customers).Error; e != nil {
		w.WriteHeader(404)
		w.Write([]byte(`{"message":"data not found"}`))
	} else {
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Response-Code", "00")
		w.Header().Set("Response-Desc", "Success")
		json.NewEncoder(w).Encode(&customers)
	}
}

// Get customers by name
func getCustomersByName(w http.ResponseWriter, r *http.Request) {
	var customers []m.Customer
	params := mux.Vars(r)
	if e := db.Where("name = ?", params["name"]).Find(&customers).Error; e != nil {
		w.WriteHeader(404)
		w.Write([]byte(`{"message":"data not found"`))
	} else {
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Response-Code", "00")
		w.Header().Set("Response-Desc", "Success")
		json.NewEncoder(w).Encode(customers)
	}
}

// Get customer by id
func getCustomerByID(w http.ResponseWriter, r *http.Request) {
	var customer m.Customer
	params := mux.Vars(r)
	if e := db.Where("id = ?", params["id"]).First(&customer).Error; e != nil {
		w.WriteHeader(404)
		w.Write([]byte(`{"message":"data not found"`))
	} else {
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Response-Code", "00")
		w.Header().Set("Response-Desc", "Success")
		json.NewEncoder(w).Encode(customer)
	}
}

// Insert customer
func insertCustomer(w http.ResponseWriter, r *http.Request) {
	var customer m.Customer
	_ = json.NewDecoder(r.Body).Decode(&customer)
	db.Create(&customer)
	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Response-Code", "00")
	w.Header().Set("Response-Desc", "Success")
	json.NewEncoder(w).Encode(customer)
}

// Update customer by id
func updateCustomerByID(w http.ResponseWriter, r *http.Request) {
	var customer m.Customer
	params := mux.Vars(r)
	if e := db.Where("id = ?", params["id"]).First(&customer).Error; e != nil {
		w.WriteHeader(404)
		w.Write([]byte(`{"message":"data not found"`))
	} else {
		_ = json.NewDecoder(r.Body).Decode(&customer)
		db.Save(&customer)
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Response-Code", "00")
		w.Header().Set("Response-Desc", "Sucess")
		json.NewEncoder(w).Encode(&customer)
	}
}

// Delete customer by id
func deleteCustomer(w http.ResponseWriter, r *http.Request) {
	var customer m.Customer
	params := mux.Vars(r)
	if e := db.Where("id = ?", params["id"]).First(&customer).Error; e != nil {
		w.WriteHeader(404)
		w.Write([]byte(`{"message":"data not found"}`))
	} else {
		db.Where("id = ?", params["id"]).Delete(&customer)
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Response-Code", "00")
		w.Header().Set("Response-Desc", "Success")
	}
}
