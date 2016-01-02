import psycopg2


class PGService:

    def __init__(
            self,
            host='localhost',
            port='5432',
            dbname='postgres',
            user='postgres',
            password='postgres'):
        try:
            self.host = host
            self.port = port
            self.dbname = dbname
            self.user = user
            self.password = password
            self.constr = ("host='%s' port='%s' dbname='%s' user='%s'  password='%s'") % (
                self.host, self.port, self.dbname, self.user, self.password)
            self.conn = psycopg2.connect(self.constr)

            self.conn.set_isolation_level(0)
            self.cur = self.conn.cursor()
        except psycopg2.Error as e:
            print (e.pgcode, e.pgerror)
            print (
                "Not able to connect to database with %s, %s, %s, %s" %
                (self.host, self.port, self.dbname, self.user))

    def set_data(self, query):
        self.cur.execute(query)

    def get_data(self, query):
        self.cur.execute(query)
        return self.cur.fetchall()
