package main
import (
	"fmt"
	"log"
	"database/sql"
	_ "github.com/lib/pq"
)
const (
    DB_USER     = "postgres"
    DB_PASSWORD = "postgres"
    DB_NAME     = "postgres"
)

func main() {
    dbinfo := fmt.Sprintf("user=%s password=%s dbname=%s sslmode=disable",
        DB_USER, DB_PASSWORD, DB_NAME)
    db, err := sql.Open("postgres", dbinfo)
		if err != nil{
			log.Println(err)
		}
    defer db.Close()

  var num int
  db.QueryRow(`select 2^3 as num`).Scan(&num)
	log.Println(num)

}
