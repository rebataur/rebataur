from ctypes import cdll
from sys import platform
import random

class FindMax():
	def __init__(self):
		if platform == "darwin":
			ext = "dylib"
		else:
			ext = "so"

		lib = cdll.LoadLibrary(
			'/home/ranjan/Downloads/rust-ffi-examples/librmax.' + ext)
		self.rmax = lib.rmax
		print("Loaded dll")
		self.count = random.randint(100,1000)
		

	def rustmax(self,i,j):
		return self.rmax(i,j)
		#return self.count
	def pymax(self,i,j):

		if i > j:
		 	return i 
		else: 
			return j

		#return self.count

if  __name__ == "__main__":
	f = FindMax()
	for i in range(1,1000000):
		r = random.randint(0,i)
		f.rmax(i,r)

	print(f.count)
	print("done r max")
	for i in range(1,1000000):
		r = random.randint(0,i)
		f.pymax(i,r)

	print("done rpy max")
