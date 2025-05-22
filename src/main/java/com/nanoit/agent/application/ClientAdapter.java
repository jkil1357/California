package com.nanoit.agent.application;

import com.nanoit.agent.domain.ShortMessage;

public interface ClientAdapter {
    boolean send(ShortMessage message);
}
//모든 고객사 어댑터(KT, 나노아이티)가 구현해야 할 전송 메서드를 하나로 통일합니다.
