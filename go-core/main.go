package bridge

import (
	"context"
	"fmt"
	"strings"
	"time"

	"github.com/sandertv/gophertunnel/minecraft"
	"github.com/sandertv/gophertunnel/minecraft/protocol"
	"github.com/sandertv/gophertunnel/minecraft/protocol/login"
	"github.com/sandertv/gophertunnel/minecraft/protocol/packet"
)

func ConnectToServer(serverAddr string, nickname string, deviceOS int32, deviceModel string) string {
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
		// Указываем, что хотим использовать самый свежий протокол
		Protocol: protocol.DefaultProtocol,
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	conn, err := dialer.DialContext(ctx, "raknet", serverAddr)
	if err != nil {
		return fmt.Sprintf("Ошибка: %v", err)
	}
	defer conn.Close()
    
    // Небольшая пауза, чтобы клиент успел прогрузить мир (опционально)
    _ = packet.Text{} 

	return "Успешно подключено!"
}
