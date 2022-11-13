# SConnect

[![Build Status](https://travis-ci.org/niltonvasques/simplecov-shields-badge.svg?branch=master)](https://travis-ci.org/niltonvasques/simplecov-shields-badge)
![stability-stable](https://img.shields.io/badge/stability-stable-green.svg)
![minimumSDK](https://img.shields.io/badge/minSDK-21-f39f37)
![stable version](https://img.shields.io/badge/stable_version-v1.0-blue)
![Repository size](https://img.shields.io/github/repo-size/smith8h/SConnect)
<br/>

**(S-Connect)** A Http client based library that use *OkHttp3* for simply making requests to URLs and APIs, and get a response as Json or plain text.

<br/>

**Content**
- [**Setup**](#setup)
- [**Documentations**](#documentations)
- [**Donations :heart:**](#donations)
<br/>

# Setup
> **Step 1.** Add the JitPack repository to your build file.</br>
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```
> **Step 2.** Add the dependency:
```gradle
dependencies {
    implementation 'com.github.smith8h:SConnect:v1.0'
}
```

<br/>

# Documentation
To create a connect first:
```java
    SConnect connect = new SConnect(this);
```
Then pass the callback interface to deal with the response:
```java
    connect.setCallBack(new SConnectCallBack() {
        @Override
        public void responseError(String tag, String message) {}
            
        @Override
        public void response(String tag, String response, HashMap<String, Object> responseHeaders) {
                
            if (SConnect.isResponseValidJson(response)) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "response is plain text", Toast.LENGTH_SHORT).show();
            }
        }
    });
```
After that you can make a connect to get the response needed:
```java
    if (SConnect.isDeviceConnected(this)) {
        connect.connect(SConnect.GET, url, "SomeTagToRecognize");
    }
```
**Available Request Methods**: `GET`, `POST`, `PUT`, `DELETE`.

<br/>

If you need to add some headers to the request:
```java
    // create a hashmap to set the headers
    HashMap<String, Object> headers = new HashMap<>();
    headers.put("key", value);
    ...
    
    // pass it to the connect
    // before calling .connect(...) method.
    connect.setHeaders(headers);
```
If you need to add some params to the request:
```java
    // create a hashmap to set the params
    HashMap<String, Object> headers = new HashMap<>();
    headers.put("key", value);
    ...
    
    // pass it to the connect
    // before calling .connect(...) method.
    connect.setParams(headers, SConnect.PARAM); // or SConnect.BODY
```

<br/>

**Getters methods** 
- `getHeaders()` returns the headers passed as (HashMap<String, Object>) Object
- `getParams()` returns the params passed as (HashMap<String, Object>) Object
- `getActivity()` returns the current Activity
- `getConnectType()` returns 0/1 as passed to the params type (PARAM, BODY values)

<br/>

# Donations
> If you would like to support this project's further development, the creator of this projects or the continuous maintenance of the project **feel free to donate**.
Your donation is highly appreciated. Thank you!
<br/>

You can **choose what you want to donate**, all donations are awesome!</br>
<br/>

[![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)](https://www.paypal.me/husseinshakir)
[![Buy me a coffee](https://img.shields.io/badge/Buy_Me_A_Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://www.buymeacoffee.com/HusseinShakir)
[![Ko-fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/husseinsmith)
<br/>

<p align="center">
  <img src="https://raw.githubusercontent.com/smith8h/smith8h/main/20221103_150053.png" style="width: 38%;"/>
  <br><b>With :heart:</b>
</p>
