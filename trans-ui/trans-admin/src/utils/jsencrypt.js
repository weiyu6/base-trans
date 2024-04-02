/** ***
 * RSA加密解密工具类
 * **/
import {JSEncrypt} from 'jsencrypt'
// 公钥
const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5vnEnXP9AD3IjdwSsxME6dXChF07q1xIYjcU/MNMlDLDIr+v+/SUO4qkoHgyhnq36962nuPbxWqSkGB6DMpIQxLF7VPv1O47y+GoKb0XaC5+SWQrgXlrNOBckrwFRVQ1ruXFKYAcGl8YVG4fVL2qRyU58r61gDMjbQFBP2XAcnwIDAQAB'

// 加密
export function encrypt(data) {
  // 新建JSEncrypt对象
  const encryptor = new JSEncrypt()
  // 设置公钥
  encryptor.setPublicKey(publicKey)
  // 加密数据
  return encryptor.encrypt(data)
}

