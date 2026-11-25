package bridge

import (
	"context"
	"fmt"
	"time"

	"github.com/sandertv/gophertunnel/minecraft"
	"github.com/sandertv/gophertunnel/minecraft/protocol/login"
)

type BridgeConfig struct {
	ServerAddr  string
	Nickname    string
	DeviceOS    int32
	DeviceModel string
}

func ConnectToServer(config BridgeConfig) string {
	dialer := minecraft.Dialer{
		IdentityData: login.IdentityData{
			DisplayName: config.Nickname,
			Identity:    "00000000-0000-0000-0000-000000000000",
		},
		ClientData: login.ClientData{
			DeviceOS:    config.DeviceOS,
			DeviceModel: config.DeviceModel,
			LanguageCode: "ru_RU",
		},
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	conn, err := dialer.DialContext(ctx, "raknet", config.ServerAddr)
	if err != nil {
		return fmt.Sprintf("Ошибка: %v", err)
	}
	defer conn.Close()

	return "Успешно подключено!"
}
