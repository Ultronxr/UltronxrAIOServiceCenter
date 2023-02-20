package cn.ultronxr.gameregister.api.steam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/02/18 13:47
 * @description
 */
@Component
@Slf4j
public class SteamAPI {

    @Value("${game-register.steam.webAPIKey}")
    private String steamWebAPIKey;

    @Value("${game-register.steam.steamID}")
    private String steamID;

    private String ISteamApps_GetAppList = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";

    private String IPlayerService_GetOwnedGames = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?format=json&key={{steamWebAPIKey}}&steamid={{steamID}}&include_appinfo=true&include_played_free_games=true";

    private String Other_appdetails = "https://store.steampowered.com/api/appdetails?appids={{appID}}&cc=CN";


    public List<Map<String, Object>> getAppList() {

        return null;
    }

    public List<Object> getOwnedGames(String steamWebAPIKey, String steamID) {

        return null;
    }

    public List<Object> getOwnedGames() {
        return getOwnedGames(this.steamWebAPIKey, this.steamID);
    }

}
