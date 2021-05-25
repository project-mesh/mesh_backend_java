package com.mesh.backend.service;


public interface IMailService {
    void send(String receiver, String title, String content);
}
