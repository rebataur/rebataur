from ctypes import cdll
from sys import platform

if platform == "darwin":
    ext = "dylib"
else:
    ext = "so"

lib = cdll.LoadLibrary(
    '/home/ranjan/Downloads/rust-ffi-examples/librmax.' + ext)
rmax = lib.rmax
