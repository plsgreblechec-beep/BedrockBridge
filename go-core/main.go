package bridge

import (
	"context"
	"fmt"
	"time"

	"github.com/sandertv/gophertunnel/minecraft"
	"github.com/sandertv/gophertunnel/minecraft/protocol"
	"github.com/sandertv/gophertunnel/minecraft/protocol/login"
)

func ConnectToServer(serverAddr string, nickname string, deviceOS int32, deviceModel string) string {
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
