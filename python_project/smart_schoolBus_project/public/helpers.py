# -*- coding: utf-8 -*-
"""
    sxw 2016-7-8

    一些小的帮助函数
"""
import base64
import functools
import inspect
import struct
import random, string
import datetime
import json
import time
from io import BytesIO
import six
from six.moves import urllib
from flask import current_app


def format_date(value, format_='%Y-%m-%d'):
    """
    将日期格式化成字符串

    :param value: 将要格式化的date或者datetime对象
    :param format_: 格式化字符串，默认为'%Y-%m-%d'，也可以为'%Y-%m-%d %H:%M:%S'等
    """
    return value.strftime(format_)


def datetime_stamp(value, format_='%Y-%m-%d %H:%M:%S'):
    """
    将日期转化为时间戳
    :param value: 将要格式化的str对象
    :param format_: 格式化字符串，默认为'%Y-%m-%d %H:%M:%S'
    """
    return time.mktime(time.strptime(value, format_))


def str_now():
    """
    获取当前时间字符串表示
    """
    return format_date(datetime.datetime.now(), "%Y-%m-%d %H:%M:%S")


def txt_wrap_by(start_str, end, html):
    """
    截取字符串
    :param start_str:
    :param end:
    :param html:
    :return:
    """
    start = html.find(start_str)
    if start >= 0:
        start += len(start_str)
        end = html.find(end, start)
        if end >= 0:
            return html[start:end].strip()


class ClassPropertyDescriptor(object):
    """
    类属性装饰器
    ref: http://stackoverflow.com/questions/5189699/how-can-i-make-a-class-property-in-python
    """

    def __init__(self, fget, fset=None):
        self.fget = fget
        self.fset = fset

    def __get__(self, obj, klass=None):
        if klass is None:
            klass = type(obj)
        return self.fget.__get__(obj, klass)()

    def __set__(self, obj, value):
        if not self.fset:
            raise AttributeError("can't set attribute")
        type_ = type(obj)
        return self.fset.__get__(obj, type_)(value)

    def setter(self, func):
        if not isinstance(func, (classmethod, staticmethod)):
            func = classmethod(func)
        self.fset = func
        return self


def classproperty(func):
    """
    类属性装饰器
    ref: http://stackoverflow.com/questions/5189699/how-can-i-make-a-class-property-in-python
    """
    if not isinstance(func, (classmethod, staticmethod)):
        func = classmethod(func)

    return ClassPropertyDescriptor(func)


def json_loads(obj):
    """
    将json字符串转换成对象。
    :param obj: str,待处理的字符串
    :return: json object

    ref: 引用自运行中心方法
    """
    # 设置encoding以支持中文，strict以支持控制字符
    return json.loads(obj, cls=json.JSONDecoder, encoding="utf-8", strict=False)


def json_dumps(obj):
    """
    将对象转换成json字符串。默认json.dumps无法序列化Datetime对象，这里进行了特殊处理。
    :param obj: obj,待处理的对象
    :return: json string
    :ref: 运行中心项目BCloud BCloud\src\utils.py json_dumps
    """
    import datetime

    class MyEncoder(json.JSONEncoder):
        def default(self, obj_):
            if isinstance(obj_, datetime.datetime):
                return str(obj_)

            return json.JSONEncoder.default(self, obj_)

    return json.dumps(obj, cls=MyEncoder, ensure_ascii=False)


def GenRandomStr(length):
    """
    生成指定长度的随机字符串
    :param length: 生成字符串的长度
    :return: 生成的字符串
    """
    num_Of_Num = random.randint(1, length-1)
    num_Of_Letter = length - num_Of_Num
    # 选中numOfNum个数字
    slc_Num = [random.choice(string.digits) for i in range(num_Of_Num)]
    # 选中numOfLetter个字母
    slc_Letter = [random.choice(string.ascii_letters) for i in range(num_Of_Letter)]
    # 打乱这个组合
    slc_Char = slc_Num + slc_Letter
    random.shuffle(slc_Char)
    # 生成密码
    result = ''.join([i for i in slc_Char])
    return result

#################################################################################
# ref https://github.com/google/oauth2client/blob/master/oauth2client/_helpers.py
#################################################################################


POSITIONAL_WARNING = 'WARNING'
POSITIONAL_EXCEPTION = 'EXCEPTION'
POSITIONAL_IGNORE = 'IGNORE'
POSITIONAL_SET = frozenset([POSITIONAL_WARNING, POSITIONAL_EXCEPTION,
                            POSITIONAL_IGNORE])

positional_parameters_enforcement = POSITIONAL_WARNING

_SYM_LINK_MESSAGE = 'File: {0}: Is a symbolic link.'
_MISSING_FILE_MESSAGE = 'Cannot access {0}: No such file or directory'


def positional(max_positional_args):
    """A decorator to declare that only the first N arguments my be positional.

    This decorator makes it easy to support Python 3 style keyword-only
    parameters. For example, in Python 3 it is possible to write::

        def fn(pos1, *, kwonly1=None, kwonly1=None):
            ...

    All named parameters after ``*`` must be a keyword::

        fn(10, 'kw1', 'kw2')  # Raises exception.
        fn(10, kwonly1='kw1')  # Ok.

    Example

    To define a function like above, do::

        @positional(1)
        def fn(pos1, kwonly1=None, kwonly2=None):
            ...

    If no default value is provided to a keyword argument, it becomes a
    required keyword argument::

        @positional(0)
        def fn(required_kw):
            ...

    This must be called with the keyword parameter::

        fn()  # Raises exception.
        fn(10)  # Raises exception.
        fn(required_kw=10)  # Ok.

    When defining instance or class methods always remember to account for
    ``self`` and ``cls``::

        class MyClass(object):

            @positional(2)
            def my_method(self, pos1, kwonly1=None):
                ...

            @classmethod
            @positional(2)
            def my_method(cls, pos1, kwonly1=None):
                ...

    The positional decorator behavior is controlled by
    ``_helpers.positional_parameters_enforcement``, which may be set to
    ``POSITIONAL_EXCEPTION``, ``POSITIONAL_WARNING`` or
    ``POSITIONAL_IGNORE`` to raise an exception, log a warning, or do
    nothing, respectively, if a declaration is violated.

    Args:
        max_positional_arguments: Maximum number of positional arguments. All
                                  parameters after the this index must be
                                  keyword only.

    Returns:
        A decorator that prevents using arguments after max_positional_args
        from being used as positional parameters.

    Raises:
        TypeError: if a key-word only argument is provided as a positional
                   parameter, but only if
                   _helpers.positional_parameters_enforcement is set to
                   POSITIONAL_EXCEPTION.
    """

    def positional_decorator(wrapped):
        @functools.wraps(wrapped)
        def positional_wrapper(*args, **kwargs):
            if len(args) > max_positional_args:
                plural_s = ''
                if max_positional_args != 1:
                    plural_s = 's'
                message = ('{function}() takes at most {args_max} positional '
                           'argument{plural} ({args_given} given)'.format(function=wrapped.__name__,
                                                                          args_max=max_positional_args,
                                                                          args_given=len(args), plural=plural_s))
                if positional_parameters_enforcement == POSITIONAL_EXCEPTION:
                    raise TypeError(message)
                elif positional_parameters_enforcement == POSITIONAL_WARNING:
                    current_app.logger.warning(message)
            return wrapped(*args, **kwargs)

        return positional_wrapper

    if isinstance(max_positional_args, six.integer_types):
        return positional_decorator
    else:
        args, _, _, defaults = inspect.getargspec(max_positional_args)
        return positional(len(args) - len(defaults))(max_positional_args)


def scopes_to_string(scopes):
    """Converts scope value to a string.

    If scopes is a string then it is simply passed through. If scopes is an
    iterable then a string is returned that is all the individual scopes
    concatenated with spaces.

    Args:
        scopes: string or iterable of strings, the scopes.

    Returns:
        The scopes formatted as a single string.
    """
    if isinstance(scopes, six.string_types):
        return scopes
    else:
        return ' '.join(scopes)


def string_to_scopes(scopes):
    """Converts stringifed scope value to a list.

    If scopes is a list then it is simply passed through. If scopes is an
    string then a list of each individual scope is returned.

    Args:
        scopes: a string or iterable of strings, the scopes.

    Returns:
        The scopes in a list.
    """
    if not scopes:
        return []
    elif isinstance(scopes, six.string_types):
        return scopes.split(' ')
    else:
        return scopes


def parse_unique_urlencoded(content):
    """Parses unique key-value parameters from urlencoded content.

    Args:
        content: string, URL-encoded key-value pairs.

    Returns:
        dict, The key-value pairs from ``content``.

    Raises:
        ValueError: if one of the keys is repeated.
    """
    urlencoded_params = urllib.parse.parse_qs(content)
    params = {}
    for key, value in six.iteritems(urlencoded_params):
        if len(value) != 1:
            msg = ('URL-encoded content contains a repeated value:'
                   '%s -> %s' % (key, ', '.join(value)))
            raise ValueError(msg)
        params[key] = value[0]
    return params


def update_query_params(uri, params):
    """Updates a URI with new query parameters.

    If a given key from ``params`` is repeated in the ``uri``, then
    the URI will be considered invalid and an error will occur.

    If the URI is valid, then each value from ``params`` will
    replace the corresponding value in the query parameters (if
    it exists).

    Args:
        :param uri: string, A valid URI, with potential existing query parameters.
        :params dict: A dictionary of query parameters.

    Returns:
        The same URI but with the new query parameters added.
    """
    parts = urllib.parse.urlparse(uri)
    query_params = parse_unique_urlencoded(parts.query)
    query_params.update(params)
    new_query = urllib.parse.urlencode(query_params)
    new_parts = parts._replace(query=new_query)
    return urllib.parse.urlunparse(new_parts)


def _add_query_parameter(url, name, value):
    """Adds a query parameter to a url.

    Replaces the current value if it already exists in the URL.

    Args:
        url: string, url to add the query parameter to.
        name: string, query parameter name.
        value: string, query parameter value.

    Returns:
        Updated query parameter. Does not update the url if value is None.
    """
    if value is None:
        return url
    else:
        return update_query_params(url, {name: value})


def _json_encode(data):
    return json.dumps(data, separators=(',', ':'))


def _to_bytes(value, encoding='ascii'):
    """Converts a string value to bytes, if necessary.

    Unfortunately, ``six.b`` is insufficient for this task since in
    Python2 it does not modify ``unicode`` objects.

    Args:
        value: The string/bytes value to be converted.
        encoding: The encoding to use to convert unicode to bytes. Defaults
                  to "ascii", which will not allow any characters from ordinals
                  larger than 127. Other useful values are "latin-1", which
                  which will only allows byte ordinals (up to 255) and "utf-8",
                  which will encode any unicode that needs to be.

    Returns:
        The original value converted to bytes (if unicode) or as passed in
        if it started out as bytes.

    Raises:
        ValueError if the value could not be converted to bytes.
    """
    result = (value.encode(encoding)
              if isinstance(value, six.text_type) else value)
    if isinstance(result, six.binary_type):
        return result
    else:
        raise ValueError('{0!r} could not be converted to bytes'.format(value))


def _from_bytes(value):
    """Converts bytes to a string value, if necessary.

    Args:
        value: The string/bytes value to be converted.

    Returns:
        The original value converted to unicode (if bytes) or as passed in
        if it started out as unicode.

    Raises:
        ValueError if the value could not be converted to unicode.
    """
    result = (value.decode('utf-8')
              if isinstance(value, six.binary_type) else value)
    if isinstance(result, six.text_type):
        return result
    else:
        raise ValueError(
            '{0!r} could not be converted to unicode'.format(value))


def _urlsafe_b64encode(raw_bytes):
    raw_bytes = _to_bytes(raw_bytes, encoding='utf-8')
    return base64.urlsafe_b64encode(raw_bytes).rstrip(b'=')


def _urlsafe_b64decode(b64string):
    # Guard against unicode strings, which base64 can't handle.
    b64string = _to_bytes(b64string)
    padded = b64string + b'=' * (4 - len(b64string) % 4)
    return base64.urlsafe_b64decode(padded)
