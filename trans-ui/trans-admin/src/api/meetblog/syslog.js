import request from "@/utils/request";

export default {
  sysloglist(searchObj) {
    return request({
      url: `/online/syslog/loglist`,
      method: 'post',
      data: searchObj
    })
  },
  sysloginfo(searchObj) {
    return request({
      url: `/online/syslog/loginfo`,
      method: 'post',
      data: searchObj
    })
  },
}
