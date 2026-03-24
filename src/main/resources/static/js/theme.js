//document.addEventListener("DOMContentLoaded", function () {
//    const THEME_KEY = "theme";
//
//    function applyTheme(theme) {
//        document.documentElement.setAttribute("data-theme", theme);
//    }
//
//    // 1️⃣ 初始化主题
//    const savedTheme = localStorage.getItem(THEME_KEY) || "light";
//    applyTheme(savedTheme);
//
//    console.log("当前主题:", savedTheme);
//
//    // 2️⃣ 绑定 toggle
//    const toggle = document.getElementById("themeToggle");
//
//    if (toggle) {
//        toggle.checked = savedTheme === "synthwave";
//
//        toggle.addEventListener("change", function () {
//            const newTheme = this.checked ? "synthwave" : "light";
//            localStorage.setItem(THEME_KEY, newTheme);
//            applyTheme(newTheme);
//
//            console.log("切换主题:", newTheme);
//        });
//    }
//});

document.addEventListener("DOMContentLoaded", function () {
    const THEME_KEY = "theme";

    function applyTheme(theme) {
        document.documentElement.setAttribute("data-theme", theme);
    }

    const savedTheme = localStorage.getItem(THEME_KEY) || "light";
    applyTheme(savedTheme);

    const toggle = document.getElementById("themeToggle");

    if (toggle) {
        // 🚨 关键：先禁用动画
        toggle.classList.add("no-animation");

        toggle.checked = savedTheme === "dark";

        // 👉 下一帧恢复动画
        requestAnimationFrame(() => {
            toggle.classList.remove("no-animation");
        });

        toggle.addEventListener("change", function () {
            const newTheme = this.checked ? "dark" : "light";
            localStorage.setItem(THEME_KEY, newTheme);
            applyTheme(newTheme);
        });
    }
});