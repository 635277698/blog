// js/blog.js - 博客页面专用脚本

const savedTheme = localStorage.getItem("theme") || "light";
document.documentElement.setAttribute("data-theme", savedTheme);

let newEditor;  // 新建文章编辑器实例

function deleteBlog(id) {
    if (confirm('确定删除吗？')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/blog/delete/' + id;
        document.body.appendChild(form);
        form.submit();
    }
}

// 打开新建文章弹窗
function openNewModal() {
    const modal = document.getElementById('newModal');
    if (modal) {
        modal.showModal();
        setTimeout(() => {
            if (!newEditor && document.getElementById('new-markdown-editor')) {
                newEditor = editormd("new-markdown-editor", {
                    width: "100%",
                    height: "100%",  // 使用百分比，继承父容器高度
                    path: "https://cdn.jsdelivr.net/npm/editor.md@1.5.0/lib/",
                    placeholder: "使用 Markdown 书写你的文章...\n\n# 标题\n## 二级标题\n\n- 列表项1\n- 列表项2\n\n```java\nSystem.out.println(\"Hello World\");\n```",
                    toolbar: true,
                    autoFocus: true,
                    imageUpload: false,
                    saveHTMLToTextarea: true,
                    emoji: true,
                    taskList: true,
                    flowChart: true,
                    sequenceDiagram: true,
                    theme: "dark",
                    previewTheme: "dark",
                    editorTheme: "pastel-on-dark",
                    onload: function() {
                        // 编辑器加载完成后刷新高度
                        this.resize();
                        // 自动聚焦
                        this.focus();
                    }
                });
            }
        }, 300);
    }
}

// 关闭新建文章弹窗
function closeNewModal() {
    const modal = document.getElementById('newModal');
    if (modal) {
        modal.close();
        if (newEditor) {
            newEditor.destroy();
            newEditor = null;
        }
        document.getElementById('new-title').value = '';
        document.getElementById('new-summary').value = '';
    }
}

// 提交新建文章
function submitNewBlog() {
    const title = document.getElementById('new-title').value;
    const content = newEditor ? newEditor.getMarkdown() : '';
    const summary = document.getElementById('new-summary').value;

    if (!title) {
        alert('请输入文章标题');
        return;
    }
    if (!content) {
        alert('请输入文章内容');
        return;
    }

    fetch('/blog/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'title=' + encodeURIComponent(title) +
              '&content=' + encodeURIComponent(content) +
              '&summary=' + encodeURIComponent(summary)
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            return response.json();
        }
    })
    .then(data => {
        if (data && !data.success) {
            alert('发布失败：' + data.message);
        }
    })
    .catch(error => {
        console.error('发布失败:', error);
        alert('发布失败，请稍后重试');
    });
}