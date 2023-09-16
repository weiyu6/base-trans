<template>
  <div class="dashboard-container">
    <!--    <div class="dashboard-text">name: {{ name }}</div>
        <div class="dashboard-text">roles: <span v-for="role in roles" :key="role">{{ role }}</span></div>-->
    <!-- 顶部内容 -->
    <el-row class="panel-group" :gutter="40">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money" @click="">
            <svg-icon icon-class="people" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">用户名：</div>
            <div class="card-name-text">{{ name }}</div>
            <!--            <count-to class="card-panel-num" :startVal="0" :endVal="IPCountForOne" :duration="3200"></count-to>-->
          </div>
        </div>
      </el-col>

      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people" @click="click('2')">
            <svg-icon icon-class="peoples" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">用户数:</div>
            <count-to class="card-panel-num" :start-val="0" :end-val="userCount" :duration="2600" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shoppingCard" @click="click('3')">
            <svg-icon icon-class="form" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">文章数:</div>
            <count-to class="card-panel-num" :start-val="0" :end-val="blogCount" :duration="3600" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message" @click="click('4')">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">评论数：</div>
            <count-to class="card-panel-num" :start-val="0" :end-val="commentCount" :duration="3000" />
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CountTo from 'vue-count-to'
import index from '@/api/meetblog/index'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name', 'roles'])
  },
  components: {
    CountTo
  },
  data() {
    return {
      IPCountForOne: 0, // 今日IP数
      userCount: 0, // 总用户数
      blogCount: 0, // 总博客数
      commentCount: 0// 总评论数
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 初始化查询
    init() {
      var searchObj = {}
      index.init(searchObj).then(res => {
        this.userCount = res.data.userCount
        this.blogCount = res.data.blogCount
      })
    },
    click(type) {
      switch (type) {
        case '2':
          this.$router.push({ path: '/user/userList' })
          break
        case '3':
          this.$router.push({ path: '/blog/blog' })
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shoppingCard {
        background: #34bfa3;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shoppingCard {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: left;
      font-weight: bold;
      margin: 26px 0 0 70px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-name-text {
        line-height: 20px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 20px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
