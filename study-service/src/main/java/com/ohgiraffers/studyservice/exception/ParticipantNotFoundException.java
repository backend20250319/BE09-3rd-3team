package com.ohgiraffers.studyservice.exception;

public class ParticipantNotFoundException extends RuntimeException {

    public ParticipantNotFoundException(Long participantId) {
        super("해당 참가자를 찾을 수 없습니다. [participantId=" + participantId + "]");
    }
}
