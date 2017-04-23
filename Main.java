package message.system.recode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

	public static Plugin plugin;

	public static Plugin getPlugin() {
		return plugin;
	}

	String cslprefix = "[MessageSystem] ";

	Inventory inv = Bukkit.createInventory(null, 54, "Trash");

	public void loadingConfiguration() {

		String prefix = "prefix";
		plugin.getConfig().addDefault(prefix, "&7[&eMinetopia VN&7] &r> ");
		String warp = "msg.cant-using-warp";
		plugin.getConfig().addDefault(warp,
				"&cMáy chủ không sử dụng &f/warp&c. Vui lòng quay lại spawn click vào NPC &5&lDỊCH CHUYỂN &cđể dịch chuyển");
		String raceprefix = "race-prefix";
		plugin.getConfig().addDefault(raceprefix, "&7[&cRaces&7] &r> ");
		String donthave = "msg.didnt-have-races";
		plugin.getConfig().addDefault(donthave, "&cBạn chưa gia nhập chủng tộc!");
		String admin = "msg.is-op";
		plugin.getConfig().addDefault(admin, "&cXin lỗi! Bạn là admin nên không thể kiểm tra!");

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

	}

	PluginDescriptionFile pdf = getDescription();

	@Override
	public void onEnable() {

		plugin = this;
		loadingConfiguration();

		getServer().getPluginManager().registerEvents(this, this);
		ConsoleCommandSender console = Bukkit.getConsoleSender();

		console.sendMessage(cslprefix + ChatColor.GREEN + "Plugin da duoc bat!");
		console.sendMessage(cslprefix + "Author: King");
		console.sendMessage(cslprefix + "Recode: Explorer");
		console.sendMessage(cslprefix + "Recode vi thang King qua ngu lon");
		console.sendMessage(cslprefix + "Version: " + pdf.getVersion());

	}

	@Override
	public void onDisable() {

		ConsoleCommandSender console = Bukkit.getConsoleSender();

		console.sendMessage(cslprefix + "Doan nay phai lam theo thang di~ bao");
		console.sendMessage("----------------------------------");
		console.sendMessage("       Disable plugin Races       ");
		console.sendMessage("   Thank for using this plugin!   ");
		console.sendMessage(ChatColor.GOLD + "              6v1.0              ");
		console.sendMessage("----------------------------------");

	}

	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		if (e.getMessage().toLowerCase().startsWith("/warp")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))
					+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("msg.cant-using-warp")));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		final Player p = e.getPlayer();
		final int tick = Integer.MAX_VALUE;

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			public void run() {

				if (p.hasPermission("food.level.1") || p.isOp()) {
					p.setFoodLevel(25);
				}

				if (p.hasPermission("food.level.2") || p.isOp()) {
					p.setFoodLevel(30);
				}

				if (p.hasPermission("chungtoc.human") || p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, tick, 2), true);
				}

				if (p.hasPermission("chungtoc.elf") || p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, tick, 1), true);
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, tick, 1), true);
				}

				if (p.hasPermission("chungtoc.orc") || p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, tick, 3), true);
				}

				if (p.hasPermission("chungtoc.wolfman") || p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, tick, 1), true);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, tick, 1), true);
				}

				if (p.hasPermission("chungtoc.dwarf") || p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, tick, 2), true);
				}

			}

		}, 200, 600);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		ConsoleCommandSender console = Bukkit.getConsoleSender();

		if (!(sender instanceof Player)) {
			console.sendMessage(cslprefix + ChatColor.RED + "Lenh chi co the su dung trong tro choi!");
			return true;
		} else {
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("trash")) {
				p.openInventory(this.inv);
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("races")) {

				if (args.length < 1) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("race-prefix"))
							+ ChatColor.RED + "Không rõ yêu cầu, vui lòng /races trogiup để xem lại lệnh!");
					return true;
				} else if (args.length == 1 && args[0].equalsIgnoreCase("trogiup")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7------------------------------------------------------------------"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"                     &cRaces Help Command                         "));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("race-prefix"))
							+ ChatColor.translateAlternateColorCodes('&',
									"&a/races trogiup &7- &fđể kiểm tra lệnh (chính là cái này)"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("race-prefix"))
							+ ChatColor.translateAlternateColorCodes('&', "&a/races me &7- &fđể kiểm tra chủng tộc"));
					if (p.isOp()) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("race-prefix"))
								+ ChatColor.translateAlternateColorCodes('&',
										"&a/races info &7- &fđể kiểm tra thông tin plugin"));
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7------------------------------------------------------------------"));
					return true;
				} else if (args.length == 1 && args[0].equalsIgnoreCase("me")) {
					if (!(p.hasPermission("chungtoc.human")) || !(p.hasPermission("chungtoc.elf"))
							|| !(p.hasPermission("chungtoc.orc")) || !(p.hasPermission("chungtoc.wolfman"))
							|| !(p.hasPermission("chungtoc.dwarf"))) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))
								+ ChatColor.translateAlternateColorCodes('&',
										getConfig().getString("msg.didnt-have-races")));
					}
					if (p.isOp()) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("race-prefix"))
								+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("msg.is-op")));
					}
					if (p.hasPermission("chungtoc.elf")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7----------------------------[&6YOUR RACES: &b&lELF&7]--------------------------"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9THÔNG TIN"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fElf(Elves) là chủng tộc cao cấp hơn ngoài người. Tuy miễn dịch"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fvới bệnh tật và không có tuổi nhưng vẫn có thể chết vì sát thương"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9ĐẶC ĐIỂM:"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fHP: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fDAMAGE: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fFARM: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fTỐC ĐỘ: &5Cao"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fNHẢY: &5Cao"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7------------------------------------------------------------------------"));
						return true;
					}
					if (p.hasPermission("chungtoc.human")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7--------------------------[&6YOUR RACES: &a&lHUMAN&7]--------------------------"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9THÔNG TIN"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fHuman là con người, tuy sức khỏe yếu hơn những tộc khácc nhưng"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fđược cái là số lượng đông và hung hăng thường xâm chiếm các tộc khác"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9ĐẶC ĐIỂM:"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fHP: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fDAMAGE: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fFARM: &5Cao"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fTỐC ĐỘ: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fNHẢY: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7------------------------------------------------------------------------"));
						return true;
					}
					if (p.hasPermission("chungtoc.orc")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7----------------------------[&6YOUR RACES: &c&lORC&7]---------------------------"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9THÔNG TIN"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fOrc là chủng tộc hung tàn nhất. Chúng phục vụ chúa tể bóng tối, tổ tiên"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&fcủa chúng chính là những người tộc Elf bị chúa tể bóng tối bắt, họ bị tra tấn"));
						p.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&fđến mức thay đổi hình dáng bên ngoài"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9ĐẶC ĐIỂM:"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fHP: &5Cao"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fDAMAGE: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fFARM: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fTỐC ĐỘ: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fNHẢY: &aBình thường"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7------------------------------------------------------------------------"));
						return true;
					}
					if (p.hasPermission("chungtoc.dwarf")) {
						p.sendMessage(
								"&7---------------------------[&6YOUR RACES: &d&lDWARF&7]--------------------------");
						p.sendMessage("&9THÔNG TIN");
						p.sendMessage("&fDwarf là tộc người lùn, rất giỏi về chế tạo và sửa chữa vật phẩm,");
						p.sendMessage("&fsức khỏe trâu hơn tộc người và elf, tộc này thường sống trong núi");
						p.sendMessage("&9ĐẶC ĐIỂM:");
						p.sendMessage("&fHP: &aBình thường");
						p.sendMessage("&fDAMAGE: &5Cao");
						p.sendMessage("&fFARM: &aBình thường");
						p.sendMessage("&fTỐC ĐỘ: &aBình thường");
						p.sendMessage("&fNHẢY: &aBình thường");
						p.sendMessage("&7------------------------------------------------------------------------");
						return true;
					}
					if (p.hasPermission("chungtoc.wolfman")) {
						p.sendMessage(
								"&7---------------------------[&6YOUR RACES: &f&lWOLFMAN&7]-----------------------");
						p.sendMessage("&9THÔNG TIN");
						p.sendMessage("&fChủng tộc người sói, tuy giống người nhưng họ lai sói và có khả năng");
						p.sendMessage("&ftriệu hồi sói, chủng tộc này là tay sai của chúa tể bóng tối");
						p.sendMessage("&9ĐẶC ĐIỂM:");
						p.sendMessage("&fHP: &aBình thường");
						p.sendMessage("&fDAMAGE: &5Cao");
						p.sendMessage("&fFARM: &aBình thường");
						p.sendMessage("&fTỐC ĐỘ: &5Cao");
						p.sendMessage("&fNHẢY: &aBình thường");
						p.sendMessage("&fChủng tộc này có khả năng nhìn trong bóng tối");
						p.sendMessage("&7------------------------------------------------------------------------");
						return true;
					}
					return true;
				}

				return true;
			}

		}

		return true;
	}

}
