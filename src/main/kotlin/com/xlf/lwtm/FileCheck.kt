package com.xlf.lwtm

import com.google.gson.Gson
import java.io.File

object FileCheck {
    private val getCommentFile = File("Comment.0.jsonl")
    private val getCounterFile = File("Counter.0.jsonl")
    private val getUsersFile = File("Users.0.jsonl")

    fun check(): Boolean {
        println("[INFO] 对文件进行检查")
        // 查找 Comment.0.jsonl 文件
        if (!getCommentFile.exists() && !getCommentFile.isFile) {
            println("\t[ERROR] 未找到 Comment.0.jsonl 文件")
            return false
        } else {
            println("\t[CHECK] 找到 Comment.0.jsonl 文件")
        }
        // 查找 Counter.0.jsonl 文件
        if (!getCounterFile.exists() && !getCounterFile.isFile) {
            println("\t[ERROR] 未找到 Counter.0.jsonl 文件")
            return false
        } else {
            println("\t[CHECK] 找到 Counter.0.jsonl 文件")
        }
        // 查找 Users.0.jsonl 文件
        if (!getUsersFile.exists() && !getUsersFile.isFile) {
            println("\t[ERROR] 未找到 Users.0.jsonl 文件")
            return false
        } else {
            println("\t[CHECK] 找到 Users.0.jsonl 文件")
        }
        return true
    }

    fun verify(): Boolean {
        println("[INFO] 对文件进行校验")
        // 获取文件并对 json 内容序列化
        try {
            val getCommentFile = getCommentFile.readLines()
            val getCounterFile = getCounterFile.readLines()
            val getUsersFile = getUsersFile.readLines()
            // 判断是否为空
            if (getCommentFile.isEmpty()) {
                println("\t[ERROR] 文件内容为空")
                return false
            }
            if (getCounterFile.isEmpty()) {
                println("\t[ERROR] 文件内容为空")
                return false
            }
            if (getUsersFile.isEmpty()) {
                println("\t[ERROR] 文件内容为空")
                return false
            }
            // 获取文件内容 json 序列化数组存储
            try {
                getCommentFile.forEach {
                    Data.commentJson.add(Gson().fromJson(it, HashMap::class.java) as HashMap<String, Any>)
                }
                getCounterFile.forEach {
                    Data.counterJson.add(Gson().fromJson(it, HashMap::class.java) as HashMap<String, Any>)
                }
                getUsersFile.forEach {
                    Data.usersJson.add(Gson().fromJson(it, HashMap::class.java) as HashMap<String, Any>)
                }
            } catch (e: Exception) {
                println("\t[ERROR] 文件内容不是 json 格式")
                return false
            }
        } catch (e: Exception) {
            println("\t[ERROR] 文件读取错误")
            return false
        }
        return true
    }
}