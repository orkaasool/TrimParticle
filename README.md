> **Short & Punchy:** A lightweight Paper plugin that adds unique, customizable particle auras to players wearing full sets of trimmed armor. No resource pack required!

---

# ✨ TrimParticles

**Feeling dull in your brand new armor trims? Bring your gear to life!** 

This is a Minecraft Paper plugin (1.21+) that rewards players for collecting and wearing a full set of matching armor trims by surrounding them with unique particle effects. Whether it's a shadowy aura of Sculk Souls for the Silence trim or a breezy Gust effect for the Flow trim, this plugin adds a fresh layer of visual progression to your server.

### 🌟 Key Features

*   **Zero Client Requirements:** 100% server-side. Completely resource-pack free and works instantly for vanilla clients joining your server.
*   **Highly Customizable:** Features a wide range of customizable functions. Easily pair different trim patterns with different particle types, tweak particle density, and adjust spawn rates. 
*   **Optimal Performance:** Built specifically for the Paper API. Uses an efficient, cached background scheduler instead of heavy event listeners to ensure zero impact on your server's TPS.

### 🚀 Installation

1. Download the latest `.jar` file from the [Releases](link-to-releases) page.
2. Drop the plugin into your server's `plugins` folder.
3. Restart your server.
4. Equip a full 4-piece set of matching trimmed armor and enjoy the effects!

### ⚙️ Configuration (Example)

```yaml
# How often particles spawn (in ticks. 20 ticks = 1 second)
spawn-interval: 10

# Configure your trim-to-particle mappings
# Format: trim_name: PARTICLE_TYPE
trims:
  silence: SCULK_SOUL
  flow: GUST
