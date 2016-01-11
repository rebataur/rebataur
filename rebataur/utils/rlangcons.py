rscatterplot = """

create or replace language plpython2u;

drop function if exists rscatterplot(text);


CREATE OR REPLACE FUNCTION rscatterplot (param_sql text)
  RETURNS bytea
AS $$
import pyRserve
conn = pyRserve.connect()


row = plpy.execute(param_sql)


conn.r.xlbl = row[0]['xlab']
conn.r.ylbl = row[0]['ylab']

conn.r.xvar = [i['xvar'] for i in row]
conn.r.yvar = [i['yvar'] for i in row]


prog = '''
	graphics.off()
	pid <- Sys.getpid()
	filename <- paste('plot_',pid,'.png',sep="")
	png(width=480, height=480, file=filename)

	dat <- data.frame(
                  xvar,
                  yvar
				  )
	library(ggplot2)

	#print(ggplot(dat, aes(x=xvar, y=yvar)) +  geom_point(shape=1)  )    # Use hollow circles

	plot(xvar,yvar,col="blue", xlab=xlbl, ylab = ylbl)

	dev.off()
	im <- readBin(filename,"raw", 999999)
	result_vector <- im

	'''

return conn.eval(prog)


$$ LANGUAGE plpython2u;
"""

