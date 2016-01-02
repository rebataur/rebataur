from setuptools import setup, find_packages
setup(
    name='rebfdw',
    version='0.0.1',
    author='Priya Ranjan',
    license='Mozilla Public License',
    packages=['reb_fdw', 'reb_main'],
    install_requires=[
        'csvkit',
        'psycopg2',
        'tweepy',
        'pyowm',
        'bottle',
        'docker-py',
        'pyRserve',  # numpy via pip
        #'pyhs2' #manual
    ],
)
