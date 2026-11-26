package bridge

import (
	"context"
	"fmt"
	"strings"
	"time"

	"github.com/sandertv/gophertunnel/minecraft"
	"github.com/sandertv/gophertunnel/minecraft/protocol"
	"github.com/sandertv/gophertunnel/minecraft/protocol/login"
)

func ConnectToServer(serverAddr string, nickname string, deviceOS int32, deviceModel string) string {
	// 1. Автоматически добавляем порт, если его нет
	if !strings.Contains(serverAddr, ":") {
		serverAddr += ":19132"
	}

	dialer := minecraft.Dialer{
		IdentityData: login.IdentityData{
			DisplayName: nickname,
			Identity:    "00000000-0000-0000-0000-000000000000",
		},
		ClientData: login.ClientData{
			DeviceOS:    protocol.DeviceOS(deviceOS),
			DeviceModel: deviceModel,
			LanguageCode: "ru_RU",
		},
		// 2. Указываем протокол 0. Это значит "использовать самый новый протокол, 
		// который поддерживает библиотека" (сейчас это 1.21.x / 1.26.x)
		Protocol: 0,
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	conn, err := dialer.DialContext(ctx, "raknet", serverAddr)
	if err != nil {
		return fmt.Sprintf("Ошибка: %v", err)
	}
	defer conn.Close()

	return "Успешно подключено!"
}
