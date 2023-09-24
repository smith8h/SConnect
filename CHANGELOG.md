
# CHANGELOG

## v4.1 Latest

- New `addParam` method to add param separately.
- New `paramsType` method to set params type as body or params.
- Rename `params` to `addParams` and remove params type argument to `paramsType` method.
- New `addHeader` method to add headers separately.
- Rename `headers` to `addHeaders`.

**Full Changelog**: [v4.0...v4.1](https://github.com/smith8h/SConnect/compare/v4.0...v4.1)

## v4.0

- Update sample code in `MainActivity` class.
- Add new `PATCH .patch()` request method.
- Add new `OPTIONS .options()` request method.
- Add new `HEAD .head()` request method.
- Replace `throw Exceptions` with `SConnectCallBack.onFailure` callback.
- Update `okhttp, okio` and `gson` dependencies.

**Full Changelog**: [v3.3...v4.0](https://github.com/smith8h/SConnect/compare/v3.3...v4.0)

## v3.3

- Code improvements.
- Added Javadoc to `SConnect`, `SConnectCallBack`.
- Added Javadoc to `SResponse.Array` class.
- Added Javadoc to `SResponse.Map` class.
- README Docs updated.

**Full Changelog**: [v3.2...v3.3](https://github.com/smith8h/SConnect/compare/v3.2...v3.3)

## v3.2

- new `forEach(..)` loop to iterate through items in array response & keys and values in map response.
- new `tag(..)` method to recognize connections without using tags inside post, delete, put and get methods.

**Full Changelog**: [v3.1...v3.2](https://github.com/smith8h/SConnect/compare/v3.1...v3.2)

## v3.1

- `runOnUiThread` replaced to `Handler` due to some bugs occurred in Service classes.
- `Map<String, String>` replaced to `Map<String, Object>` in headers and params.

**Full Changelog**: [v3.0...v3.1](https://github.com/smith8h/SConnect/compare/v3.0...v3.1)

## v3.0

- Fix some bugs.
- Code and implementation improvements.

**Full Changelog**: [v2.0...v3.0](https://github.com/smith8h/SConnect/compare/v2.0...v3.0)

## v2.0

- `response(...) {...}` method in SConnectCallBack updated to `onSuccess(SResponse response, String tag, HashMap<String, Object> headers) { ...}`
- `responseError(...) {...}` method in SConnectCallBack updated to `onFailure(SResponse response, String tag) { ...}`
- new `SResponse` class to parse and deal with json response.

**Full Changelog**: [v1.0...v2.0](https://github.com/smith8h/SConnect/compare/v1.0...v2.0)
