import $ from 'jquery';

// TODO: change this to "" in production
const urlPrefix = "";

export const post = (url, body, callback, authToken="") => {
    if (authToken === "") {
        console.warn("authToken is '', should pass token")
        authToken = localStorage.getItem("auth")
    }

    $.ajax({
        url: urlPrefix + url,
        type: 'POST',
        dataType: 'json',
        headers: {
            'Authorization': "Bearer " + authToken,
        },
        data: JSON.stringify(body),
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            callback(result)
        },
        error: function (result) {
            callback(result)
        }
    });
}


export const get = (url, callback, authToken = "") => {
    if (authToken === "") {
        console.warn("authToken is '', should pass token")
        authToken = localStorage.getItem("auth")
    }
    $.ajax({
        url: urlPrefix + url,
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': "Bearer " + authToken,
        },
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            callback(result)
        },
        error: function (result) {
            callback(result)
        }
    });

}


export const del = (url, callback, authToken="") => {
    if (authToken === "") {
        console.warn("authToken is '', should pass token")
        authToken = localStorage.getItem("auth")
    }

    $.ajax({
        url: urlPrefix + url,
        type: 'DELETE',
        dataType: 'json',
        headers: {
            'Authorization': "Bearer " + authToken,
        },
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            callback(result)
        },
        error: function (result) {
            callback(result)
        }
    });

}