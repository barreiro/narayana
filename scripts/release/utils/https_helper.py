import httplib


def request(method, host, path, body=None, headers={}):
    """
    Generic HTTPS request method
    """
    connection = httplib.HTTPSConnection(host)
    connection.request(method, path, body, headers)
    return connection.getresponse()


def get(host, path, body=None, headers={}):
    """
    HTTPS GET request
    """
    return request('GET', host, path, body, headers)


def post(host, path, body=None, headers={}):
    """
    HTTPS POST request
    """
    return request('POST', host, path, body, headers)


def put(host, path, body=None, headers={}):
    """
    HTTPS PUT request
    """
    return request('PUT', host, path, body, headers)
