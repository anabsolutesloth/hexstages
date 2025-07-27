# Hexstages

[![powered by hexdoc](https://img.shields.io/endpoint?url=https://hexxy.media/api/v0/badge/hexdoc?label=1)](https://github.com/hexdoc-dev/hexdoc)

Hexcasting x Gamestages compatibility mod, allowing restricting casting specific hex Actions when players lack specified Stages.

To configure, create a Datapack with JSON files in `/hexstages/staged_actions/` under a namespace, and associate a Stage to a list of Hex Action IDs.


### Example file
```json
{
  "expert": [
    "hexcasting:teleport/great",
    "hexcasting:flight/time"
  ]
}
```
This configuration will restrict the casting of **Greater Teleport** and **Wayfarer's Flight**, to require the "expert" Stage.
Casting a staged action without the required Stage will cause a 3 second Blindness mishap.