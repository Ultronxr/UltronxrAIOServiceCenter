# riot-auth Copyright (c) 2022 Huba Tuba (floxay)
# Licensed under the MIT license. Refer to the LICENSE file in the project root for more information.

import asyncio
import sys

import riot_auth

# region asyncio.run() bug workaround for Windows, remove below 3.8 and above 3.10.6
if sys.platform == "win32":
    asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())
# endregion

username = sys.argv[1]
password = sys.argv[2]
CREDS = username, password

auth = riot_auth.RiotAuth()
asyncio.run(auth.authorize(*CREDS))

print(f"Access Token Type: {auth.token_type}")
print(f"Access Token: {auth.access_token}")
print(f"Entitlements Token: {auth.entitlements_token}")
print(f"User ID: {auth.user_id}")

# Reauth using cookies. Returns a bool indicating whether the reauth attempt was successful.
asyncio.run(auth.reauthorize())
